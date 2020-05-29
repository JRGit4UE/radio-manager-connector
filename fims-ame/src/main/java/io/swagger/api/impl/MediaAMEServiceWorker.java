package io.swagger.api.impl;

import io.swagger.model.*;
import java.util.*;
import java.math.BigDecimal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.util.*;
import javax.activation.MimetypesFileTypeMap;

class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    
    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);    
            } catch (IOException ioe)
              {
                ioe.printStackTrace();  
              }
    }
}

public class MediaAMEServiceWorker extends Thread {
	
	 public enum StatusEnum {
		 QUEUED,
		 RUNNING,
		 COMPLETED,
		 PAUSED,
		 CANCELLED,
		 FAILED
	 }
	 
	 protected StatusEnum status;
	 protected String sourceUrl;
	 protected String targetFolder;
	 
	 protected AmeJobType job;
	 
	public MediaAMEServiceWorker(AmeJobType _job) {
		status = StatusEnum.QUEUED;
		job = _job;
	}

	public void run() {
		
		String jobIdStr = job.getResourceID();	
		String tempFolder = MediaAMEServiceWorkerConfig.getTempFolder() + "/" + jobIdStr;
		
		// profile handling
		String profileName = "default";
		try {
			profileName = job.getProfile().getAmeTemplate().getAmeTemplateID();
		}
		catch (Exception e) {
			System.out.println("could not decode template name, using (default)");
		}
		
		boolean doProxies = true;
		boolean doFace = true;
		boolean doObjects = true;
		boolean doQuality = true;
		if (profileName.equals("proxygeneration")) {
			doFace = false;
			doObjects = false;
			doQuality = false;
		}
		else if (profileName.equals("contentanalysis")) {
			doProxies = true;
			doQuality = false;
		}
		else if (profileName.equals("fullanalysis")) {			
		}
		else if (profileName.equals("qualityanalysis")) {	
			doFace = false;
			doObjects = false;
			doProxies = true;
		}
		else if (profileName.equals("face")) {	
			doFace = true;
			doObjects = false;
			doProxies = true;
			doQuality = false;
		}
		else if (profileName.equals("object")) {	
			doFace = false;
			doObjects = true;
			doProxies = true;
			doQuality = false;
		}
		else {
			System.out.println("profile "+profileName+" unknown, using (default)");
			profileName = "fullanalysis";
		}
			
		try {		
			job.setStatus(AmeJobType.StatusEnum.RUNNING);
			job.setResourceModifiedDate(new Date());
			job.setJobStartedTime(new Date());
			
			try {
				if (job.getNotifyAt() != null) {
					if (job.getNotifyAt().getReplyTo() != null) {
						String r = sendNotification(job);

					}
				}
			}
			catch (Exception e) {
				System.out.println("failed to send notification: "+e.getMessage());
			}
		

			String tempFile = "";
			String proxyFile = "";
			String kfDir = "";
			String faceImgDir = "";
			String mdFile1 = "";
			String mdFile2 = "";
			String proxyUrl ="";
			String faceMdFile = "";
			String objMdFile = "";
			String vqMdFile = "";
			
			File tmpFile = new File(tempFolder);
			tmpFile.mkdirs();
			
			String targetFolder = MediaAMEServiceWorkerConfig.getTargetRootFolder() + "/" + jobIdStr;
			String relTargetFolder = "/" + jobIdStr;
			kfDir = targetFolder + "/keyframes";
			faceImgDir = targetFolder + "/facekeyframes";
			String relKfDir = relTargetFolder + "/keyframes";
			String relFaceImgDir = relTargetFolder + "/facekeyframes";
			String absFaceImgDir = MediaAMEServiceWorkerConfig.getTargetRootFolder() + relFaceImgDir; // FTT added
			mdFile1 = tempFolder + "/" + "kf.json";
			mdFile2 = tempFolder + "/" + "cut.json";
			faceMdFile = tempFolder + "/" + "face.json";
			objMdFile = tempFolder + "/" + "obj.json";
			vqMdFile = tempFolder + "/" + "vq.json";
		
			File targFile = new File(targetFolder);
			targFile.mkdirs();
			
			File kfFile = new File(kfDir);
			kfFile.mkdirs();


			
			// download
			System.out.println("trying to download");
			
			String url = job.getBmObject().getBmContents().get(0).getLocation();
			System.out.println(url);
			URL sourceResource = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(sourceResource.openStream());
			
			System.out.println("setting up paths");
			
			String urlPath = sourceResource.getPath();
			String fileName = urlPath.substring(urlPath.lastIndexOf('/') + 1);
			
			tempFile = tempFolder + "/" + fileName;

			System.out.println("temp file: "+tempFile);
			
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			URLConnection u = sourceResource.openConnection();
			long length = Long.parseLong(u.getHeaderField("Content-Length"));
			String mimetype = u.getHeaderField("Content-Type");
			System.out.println("mimetype: "+mimetype);
			
			System.out.println("transferred file");
			
			// check if file is an image
			boolean isImage = false;

	        File f = new File(tempFile);
			//String mimetype= new MimetypesFileTypeMap().getContentType(f);
			String mtype = mimetype.split("/")[0];
			if(mtype.equals("image")) isImage = true;
			
			String proxyExt = ".mp4";
			if (isImage) proxyExt = ".jpg";
			
			proxyFile = targetFolder + "/" + fileName + proxyExt;
			proxyUrl = MediaAMEServiceWorkerConfig.getTargetBaseUrl() + "/" + jobIdStr + "/" + fileName + proxyExt;
			
			String proxyCmd = "";
			if (doProxies) {
				if (isImage) proxyCmd = MediaAMEServiceWorkerConfig.getImageProxyCmd(tempFile, proxyFile);
				else proxyCmd = MediaAMEServiceWorkerConfig.getVideoProxyCmd(tempFile, proxyFile);
				
				System.out.println("executing cmd: " + proxyCmd);
			
				List<String> lines = Arrays.asList(
						"cd /home/tomcat\n",
						"echo calling ffmpeg\n",
						proxyCmd + "\n",
						"echo done with ffmpeg\n"
						);
				Path filep = Paths.get(tempFolder + "/runffmpeg.sh");
				Files.write(filep, lines, Charset.forName("UTF-8"));
				
				File file = new File(tempFolder + "/runffmpeg.sh");

				Set<PosixFilePermission> perms = new HashSet<>();
				perms.add(PosixFilePermission.OWNER_READ);
				perms.add(PosixFilePermission.OWNER_WRITE);
				perms.add(PosixFilePermission.OWNER_EXECUTE);
				perms.add(PosixFilePermission.GROUP_READ);
				perms.add(PosixFilePermission.GROUP_EXECUTE);
				
				Files.setPosixFilePermissions(file.toPath(), perms);
				
				 //Process pr2 = Runtime.getRuntime().exec("/home/tomcat/setgstenv.sh"+ 
				//		 " && "+"cd " + gstWD + " && " + proxyCmd + " && echo done");
				 //Process pr2 = Runtime.getRuntime().exec(cmds, env, new File(gstWD));
				 //Process pr2 = Runtime.getRuntime().exec("cd " + gstWD );
				
				Process pr = Runtime.getRuntime().exec(tempFolder + "/runffmpeg.sh");				
				
				//Process pr = Runtime.getRuntime().exec(proxyCmd);
				
	            StreamGobbler errorGobbler = new 
	                    StreamGobbler(pr.getErrorStream(), "ERROR");            
	                
	                // any output?
	                StreamGobbler outputGobbler = new 
	                    StreamGobbler(pr.getInputStream(), "OUTPUT");
	                    
	                // kick them off
	                errorGobbler.start();
	                outputGobbler.start();
	
	                
				pr.waitFor();
				
				System.out.println("done executing cmd");
			}
				
			String gstWD = MediaAMEServiceWorkerConfig.getGstWorkingDir();
			
			
			if ((!isImage) && (doProxies)) {
				
				System.out.println("starting video segmentation");
				
				proxyCmd = MediaAMEServiceWorkerConfig.getVideoSegmentationCmd(tempFile, relKfDir, mdFile2, mdFile1);
				System.out.println("executing cmd2: " + proxyCmd);
				
				String[] cmds = new String[1];
				cmds[0] = proxyCmd;
				
				String[] env = new String[1];
				env[0] = MediaAMEServiceWorkerConfig.getGstLibraryPath();
				
				// create file and set rights
				List<String> lines = Arrays.asList(
						"export " + MediaAMEServiceWorkerConfig.getGstLibraryPath(),
						"cd "+ gstWD + "\n",
						proxyCmd + "\n"
						);
				Path filep = Paths.get(tempFolder + "/rungst.sh");
				Files.write(filep, lines, Charset.forName("UTF-8"));
				
				File file = new File(tempFolder + "/rungst.sh");

				Set<PosixFilePermission> perms = new HashSet<>();
				perms.add(PosixFilePermission.OWNER_READ);
				perms.add(PosixFilePermission.OWNER_WRITE);
				perms.add(PosixFilePermission.OWNER_EXECUTE);
				perms.add(PosixFilePermission.GROUP_READ);
				perms.add(PosixFilePermission.GROUP_EXECUTE);
				
				Files.setPosixFilePermissions(file.toPath(), perms);
				
				 //Process pr2 = Runtime.getRuntime().exec("/home/tomcat/setgstenv.sh"+ 
				//		 " && "+"cd " + gstWD + " && " + proxyCmd + " && echo done");
				 //Process pr2 = Runtime.getRuntime().exec(cmds, env, new File(gstWD));
				 //Process pr2 = Runtime.getRuntime().exec("cd " + gstWD );
				
				Process pr2 = Runtime.getRuntime().exec(tempFolder + "/rungst.sh");
			
	            StreamGobbler errorGobbler2 = new 
	                    StreamGobbler(pr2.getErrorStream(), "ERROR");            
	                
	                // any output?
	                StreamGobbler outputGobbler2 = new 
	                    StreamGobbler(pr2.getInputStream(), "OUTPUT");
	                    
	                // kick them off
	                errorGobbler2.start();
	                outputGobbler2.start();

	                
				pr2.waitFor();

			
				System.out.println("done executing cmd2");
			}
			
			if (doFace || doObjects) {
				try {
						
					System.out.println("starting video analysis");
					//FTT fix face recognition with absFaceImgDir instead of relFaceImgDir
					if (!isImage) proxyCmd = MediaAMEServiceWorkerConfig.getVideoAnalysisCmd(proxyFile, absFaceImgDir, faceMdFile, objMdFile, doFace, doObjects);
					else proxyCmd = MediaAMEServiceWorkerConfig.getImageAnalysisCmd(proxyFile, absFaceImgDir, faceMdFile, objMdFile, doFace, doObjects);
					System.out.println("executing cmd3: " + proxyCmd);
					
					String[] cmds = new String[1];
					cmds[0] = proxyCmd;
					
					String[] env = new String[1];
					env[0] = MediaAMEServiceWorkerConfig.getGstLibraryPath();
					
					// create file and set rights
					List<String> lines = Arrays.asList(
							"export " + MediaAMEServiceWorkerConfig.getGstLibraryPath(),
							"cd "+ gstWD + "\n",
							proxyCmd + "\n"
							);
					Path filep = Paths.get(tempFolder + "/rungst.sh");
					Files.write(filep, lines, Charset.forName("UTF-8"));
					
					File file = new File(tempFolder + "/rungst.sh");
	
					Set<PosixFilePermission> perms = new HashSet<>();
					perms.add(PosixFilePermission.OWNER_READ);
					perms.add(PosixFilePermission.OWNER_WRITE);
					perms.add(PosixFilePermission.OWNER_EXECUTE);
					perms.add(PosixFilePermission.GROUP_READ);
					perms.add(PosixFilePermission.GROUP_EXECUTE);
					
					Files.setPosixFilePermissions(file.toPath(), perms);
					
					 //Process pr2 = Runtime.getRuntime().exec("/home/tomcat/setgstenv.sh"+ 
					//		 " && "+"cd " + gstWD + " && " + proxyCmd + " && echo done");
					 //Process pr2 = Runtime.getRuntime().exec(cmds, env, new File(gstWD));
					 //Process pr2 = Runtime.getRuntime().exec("cd " + gstWD );
					
					Process pr2 = Runtime.getRuntime().exec(tempFolder + "/rungst.sh");
				
		            StreamGobbler errorGobbler2 = new 
		                    StreamGobbler(pr2.getErrorStream(), "ERROR");            
		                
		                // any output?
		                StreamGobbler outputGobbler2 = new 
		                    StreamGobbler(pr2.getInputStream(), "OUTPUT");
		                    
		                // kick them off
		                errorGobbler2.start();
		                outputGobbler2.start();
	
		                
					pr2.waitFor();
	
				
					System.out.println("done executing cmd3");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (doQuality) {
				try {
						
					System.out.println("starting quality analysis");
					
					
					proxyCmd = MediaAMEServiceWorkerConfig.getVisualQualityAnalysisCmd(proxyFile, relFaceImgDir, vqMdFile);
					System.out.println("executing cmd4: " + proxyCmd);
					
					String[] cmds = new String[1];
					cmds[0] = proxyCmd;
					
					String[] env = new String[1];
					env[0] = MediaAMEServiceWorkerConfig.getGstLibraryPath();
					
					// create file and set rights
					List<String> lines = Arrays.asList(
							"export " + MediaAMEServiceWorkerConfig.getGstLibraryPath(),
							"cd "+ gstWD + "\n",
							proxyCmd + "\n"
							);
					Path filep = Paths.get(tempFolder + "/rungst.sh");
					Files.write(filep, lines, Charset.forName("UTF-8"));
					
					File file = new File(tempFolder + "/rungst.sh");
	
					Set<PosixFilePermission> perms = new HashSet<>();
					perms.add(PosixFilePermission.OWNER_READ);
					perms.add(PosixFilePermission.OWNER_WRITE);
					perms.add(PosixFilePermission.OWNER_EXECUTE);
					perms.add(PosixFilePermission.GROUP_READ);
					perms.add(PosixFilePermission.GROUP_EXECUTE);
					
					Files.setPosixFilePermissions(file.toPath(), perms);
					
					 //Process pr2 = Runtime.getRuntime().exec("/home/tomcat/setgstenv.sh"+ 
					//		 " && "+"cd " + gstWD + " && " + proxyCmd + " && echo done");
					 //Process pr2 = Runtime.getRuntime().exec(cmds, env, new File(gstWD));
					 //Process pr2 = Runtime.getRuntime().exec("cd " + gstWD );
					
					Process pr2 = Runtime.getRuntime().exec(tempFolder + "/rungst.sh");
				
		            StreamGobbler errorGobbler2 = new 
		                    StreamGobbler(pr2.getErrorStream(), "ERROR");            
		                
		                // any output?
		                StreamGobbler outputGobbler2 = new 
		                    StreamGobbler(pr2.getInputStream(), "OUTPUT");
		                    
		                // kick them off
		                errorGobbler2.start();
		                outputGobbler2.start();
	
		                
					pr2.waitFor();
	
				
					System.out.println("done executing cmd4");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			
			// report
			AmeReportType report = new AmeReportType();
			
			ResourceReferenceType contentRef = new ResourceReferenceType();
			contentRef.setResourceID(job.getBmObject().getBmContents().get(0).getResourceID());
			contentRef.setLocation(job.getBmObject().getBmContents().get(0).getLocation());
			report.setBmContentReference(contentRef);
			
			ToolInformationType ti = new ToolInformationType();
			ti.setName("MARCONI analysis service");
			report.setToolInformation(ti);
			
			if (doProxies) {
			
				AmeItemResultType proxyResult = new AmeItemResultType();
				proxyResult.setDetectionMethod(AmeItemResultType.DetectionMethodEnum.AUTOMATIC);
				proxyResult.setAmeItemID("http://id.projectmarconi.eu/item/proxy");
				proxyResult.setAmeItemName("Proxy Generation");
				AmeItemParameterType pparam = new AmeItemParameterType();
				pparam.setParameterName("ProxyLocation");
				pparam.setValue(proxyUrl);
				proxyResult.getAmeItemOutputs().add(pparam);
				
				report.getAmeItemResult().add(proxyResult);
	
				if (!isImage) {
				
					System.out.println("inserting segmentation results");
					
					AmeItemResultType kfResult = new AmeItemResultType();
					kfResult.setDetectionMethod(AmeItemResultType.DetectionMethodEnum.AUTOMATIC);
					kfResult.setAmeItemID("http://ebu.io/fims/cards/V003");
					kfResult.setAmeItemName("Keyframe Extraction");
					
					System.out.print("reading kf file ");
					
					String kffile = null;
					try {
						kffile = readFile(mdFile1);
						
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					if (kffile==null) kffile ="[]";
						
					System.out.println(" ... done");
	
					
					AmeItemParameterType kfparam = new AmeItemParameterType();
					kfparam.setParameterName("DetectedKeyframes");
					kfparam.setValue(kffile.replace("\"", "\\\"").replace("\n", " ").replace("\r"," "));
					kfResult.getAmeItemOutputs().add(kfparam);
					
					report.getAmeItemResult().add(kfResult);
	
					AmeItemResultType tdResult = new AmeItemResultType();
					tdResult.setDetectionMethod(AmeItemResultType.DetectionMethodEnum.AUTOMATIC);
					tdResult.setAmeItemID("http://ebu.io/fims/cards/V002");
					tdResult.setAmeItemName("Shot Boundary Detection");
					
					String cutdetfile = null;
					try {
						cutdetfile = readFile(mdFile2);
						
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					if (cutdetfile==null) cutdetfile ="[]";
	
			
					AmeItemParameterType sdparam = new AmeItemParameterType();
					sdparam.setParameterName("ShotBoundaryTimesAndClassification");
					sdparam.setValue(cutdetfile.replace("\"", "\\\"").replace("\n", " ").replace("\r"," "));
					tdResult.getAmeItemOutputs().add(sdparam);
					
					System.out.println("done");
					
					report.getAmeItemResult().add(tdResult);
				}
			}
				
			if (doObjects || doFace){
				
				System.out.println("inserting analysis results");
				
				if (doFace) {
				
					AmeItemResultType fResult = new AmeItemResultType();
					fResult.setDetectionMethod(AmeItemResultType.DetectionMethodEnum.AUTOMATIC);
					fResult.setAmeItemID("http://ebu.io/fims/cards/V009");
					fResult.setAmeItemName("Face Recognition");
					
					System.out.print("reading face file ");
					
					String facefile = null;
					try {
						facefile = readFile(faceMdFile);
						
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					if (facefile==null) facefile ="[]";
						
					System.out.println(" ... done");
	
					
					AmeItemParameterType fparam = new AmeItemParameterType();
					fparam.setParameterName("ListOfNames");
					fparam.setValue(facefile.replace("\"", "\\\"").replace("\n", " ").replace("\r"," "));
					fResult.getAmeItemOutputs().add(fparam);
					
					report.getAmeItemResult().add(fResult);
				}
				
				if (doObjects) {

					AmeItemResultType objResult = new AmeItemResultType();
					objResult.setDetectionMethod(AmeItemResultType.DetectionMethodEnum.AUTOMATIC);
					objResult.setAmeItemID("http://ebu.io/fims/cards/V020");
					objResult.setAmeItemName("Concept Detection");
					
					String objfile = null;
					try {
						objfile = readFile(objMdFile);
						
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					if (objfile==null) objfile ="[]";
	
			
					AmeItemParameterType objparam = new AmeItemParameterType();
					objparam.setParameterName("DetectedConcepts");
					objparam.setValue(objfile.replace("\"", "\\\"").replace("\n", " ").replace("\r"," "));
					objResult.getAmeItemOutputs().add(objparam);
					
					System.out.println("done");
					
					report.getAmeItemResult().add(objResult);
				}
			}
			
			if (doQuality){
				
				System.out.println("inserting analysis results");
				
				AmeItemResultType fResult = new AmeItemResultType();
				fResult.setDetectionMethod(AmeItemResultType.DetectionMethodEnum.AUTOMATIC);
				fResult.setAmeItemID("http://id.projectmarconi.eu/item/visualquality");
				fResult.setAmeItemName("Merged visual qc results");
				
				System.out.print("reading face file ");
				
				String vqfile = null;
				try {
					vqfile = readFile(vqMdFile);
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				if (vqfile==null) vqfile ="[]";
					
				System.out.println(" ... done");

				
				AmeItemParameterType fparam = new AmeItemParameterType();
				fparam.setParameterName("VisualQuality");
				fparam.setValue(vqfile.replace("\"", "\\\"").replace("\n", " ").replace("\r"," "));
				fResult.getAmeItemOutputs().add(fparam);
				
				report.getAmeItemResult().add(fResult);
			}
			
			job.getProfile().setAmeReport(report);
			
			// finish status
			job.setStatus(AmeJobType.StatusEnum.COMPLETED);
			job.setResourceModifiedDate(new Date());
			job.setJobCompletedTime(new Date());
			job.getProcessed().setPercentageProcessedCompleted(new BigDecimal(100));
			
			try {
				if (job.getNotifyAt() != null) {
					if (job.getNotifyAt().getReplyTo() != null) {
					
						
						String r = sendNotification(job);
						
						
						System.out.println("sent notification: "+r);
					}
				}
			}
			catch (Exception e) {
				System.out.println("failed to send notification: "+e.getMessage());
			}
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			
			job.setStatus(AmeJobType.StatusEnum.FAILED);
			job.setResourceModifiedDate(new Date());
			
			try {
				if (job.getNotifyAt() != null) {
					if (job.getNotifyAt().getFaultTo() != null) {
						String r = sendPostRequest(job.getNotifyAt().getFaultTo(),e.getMessage());
					}
				}
			}
			catch (Exception e2) {
				System.out.println("failed to send notification: "+e2.getMessage());
			}
		}
		
		finally {
			try {
				FileUtils.deleteDirectory(new File(tempFolder));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void cancel() {
		status = StatusEnum.CANCELLED;
	}

	public void pause() {
		status = StatusEnum.PAUSED;
	}
	
	public String sendPostRequest(String requestUrl, String payload) {
		StringBuffer jsonString = new StringBuffer();
		try {
	        URL url = new URL(requestUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	    } catch (Exception e) {
	            throw new RuntimeException(e.getMessage());
	    }
	    return jsonString.toString();
	}
	
	public String sendNotification(AmeJobType job) {
		String notifyPayload = "{\n\"mediaAMEJob\":\"";
		String jobStr = job.toString();
		// replace("\\\"", "\\\\\\\"").
		jobStr = jobStr.replace("\n", "").replace("\r", "").replace("\"", "\\\"").replace("\\\\\"", "\\\\\\\"");
		notifyPayload = notifyPayload + jobStr + "\"\n}";
		
		String r = sendPostRequest(job.getNotifyAt().getReplyTo(),notifyPayload);
		
		return r;
	}
	
	String readFile(String path) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, "UTF-8");
			}
}
