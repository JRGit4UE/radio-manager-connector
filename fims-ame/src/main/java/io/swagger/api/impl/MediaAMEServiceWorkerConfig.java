package io.swagger.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MediaAMEServiceWorkerConfig {
/*
	// FTT To create an initial config - but you have to replace ip by %s
	public static void writeFile(){
		try {
			MediaAMEServiceWorkerConfig cfg = new MediaAMEServiceWorkerConfig();
			ObjectMapper objectMapper = new ObjectMapper();
			String configName = MediaAMEServiceWorkerConfig.class.getSimpleName() + ".json";
			System.out.println("configName=" + configName);
			Path configPath = Paths.get("/home/ftt/project/marconi/FIMS_AME/fims-ame/" + configName);
			objectMapper.writeValue(configPath.toFile(), cfg);
		}
		catch(Exception e)
		{
			System.out.println("Ex: " + e.getMessage());
		}
	}
*/

	private static MediaAMEServiceWorkerConfig instance;
	private MediaAMEServiceWorkerConfig () {}

	private static Path getConfigPath() {
		String configName = MediaAMEServiceWorkerConfig.class.getSimpleName() + ".json";
		File f;
		String p;
		try{
			System.out.println("Lookup config file in $FIMS_CFG ..");
			p = System.getenv("FIMS_CFG");
			if(p != null){
				f = new File(p);
				if(f.exists() && !f.isDirectory()){
					return Paths.get(p);
				}
			}
		}
		catch(Exception e){
			System.out.println("E: " + e.getMessage());
		}
		try{
			System.out.println("Lookup config file $CATALINA_HOME/webapps/" + configName + " ..");
			p = System.getenv("CATALINA_HOME");
			if(p != null){
				p = p + "/webapps/" + configName;
				f = new File(p);
				if(f.exists() && !f.isDirectory()){
					return Paths.get(p);
				}
			}
		}
		catch(Exception e){
			System.out.println("E: " + e.getMessage());
		}
		try{
			System.out.println("Lookup config path in properties{catalina.base}/webapps/" + configName + " ..");
			p = System.getProperty("catalina.base");
			if(p != null){
				p = p + "/webapps/" + configName;
				f = new File(p);
				if(f.exists() && !f.isDirectory()){
					return Paths.get(p);
				}
			}
		}
		catch(Exception e){
			System.out.println("E: " + e.getMessage());
		}

		System.out.println("No config file found.");
		return null;
	}

	public static MediaAMEServiceWorkerConfig getInstance () {

		if (MediaAMEServiceWorkerConfig.instance == null) {

			System.out.println("MediaAMEServiceWorkerConfig: init");
			try{
				Path configPath = getConfigPath();
				System.out.println("Using configPath " + configPath);
				String cfg = new String(Files.readAllBytes(configPath));
				//System.out.println("configFile=" + cfg);
				ObjectMapper objectMapper = new ObjectMapper();
				MediaAMEServiceWorkerConfig.instance = objectMapper.readValue(cfg, MediaAMEServiceWorkerConfig.class);
			}
			catch(Exception e) {
				System.out.println("Exception MediaAMEServiceWorkerConfig: init " + e.getMessage());
			}
		}
		return MediaAMEServiceWorkerConfig.instance;
	}

	private String version = "v1.0.0";
	public String getVersion(){return version;}

	private String description = "Config file for fims-ame";
	public String getDescription(){return description;}

	private boolean gpuHost;//=true;
	public boolean getGpuHost(){return gpuHost;}
	public static boolean getgpuHost(){
		return MediaAMEServiceWorkerConfig.getInstance().getGpuHost();
	}
	
	private String targetRootFolder;//="/home/tomcat/data";
	public String gettargetRootFolder(){ return targetRootFolder;}
	public static String getTargetRootFolder() {
		return MediaAMEServiceWorkerConfig.getInstance().gettargetRootFolder();
//		return "/home/tomcat/data";
	}

	private String tempFolder;//="%s/webapps/fims2/temp";
	public String gettempFolder() {return tempFolder;}
	public static String getTempFolder() {
		try{
			return String.format(MediaAMEServiceWorkerConfig.getInstance().gettempFolder(), System.getProperty("catalina.base"));
		}
		catch(Exception e){
			System.out.println("E:tempFolder " + e.getMessage());
		}
//		return System.getProperty("catalina.base") + "/webapps/fims2/temp";
		return "";
	}

	private String 	videoProxyCmd;//="ffmpeg -y -i %s -vf scale=640:-2 -c:v libx264 -c:a copy %s";
	public String getvideoProxyCmd() {return videoProxyCmd;}
	public static String getVideoProxyCmd(String inputFile, String outputFile) {
		try{
			return String.format(MediaAMEServiceWorkerConfig.getInstance().getvideoProxyCmd(), inputFile, outputFile);
		}
		catch(Exception e){
			System.out.println("E:videoProxyCmd " + e.getMessage());
		}
//		return "ffmpeg -y -i "+ inputFile + " -vf scale=640:-2 -c:v libx264 -c:a copy " +outputFile;
		return "";
	}

	private String imageProxyCmd;//="ffmpeg -y -i %s -vf scale=640:-1 %s";
	public String getimageProxyCmd(){return imageProxyCmd;}
	public static String getImageProxyCmd(String inputFile, String outputFile) {
		try{
			return String.format(MediaAMEServiceWorkerConfig.getInstance().getimageProxyCmd(), inputFile, outputFile);
		}
		catch(Exception e){
			System.out.println("E:imageProxyCmd " + e.getMessage());
		}
//		return "ffmpeg -y -i "+ inputFile + " -vf scale=640:-1 " + outputFile;
		return "";
	}

	private String gstWorkingDirGPU;//="/home/tomcat/JRS_Gstreamer_Modules";
	public String getgstWorkingDirGPU(){return gstWorkingDirGPU;}

	private String gstWorkingDir;//="/home/tomcat/JRSGstreamerPlugins";
	public String getgstWorkingDir(){return gpuHost ? gstWorkingDirGPU : gstWorkingDir;}
	public static String getGstWorkingDir() {
		return MediaAMEServiceWorkerConfig.getInstance().getgstWorkingDir();
//		if (gpuHost) return "/home/tomcat/JRS_Gstreamer_Modules";
//		else return "/home/tomcat/JRSGstreamerPlugins";
	}

	private String gstLibraryPath;//="LD_LIBRARY_PATH=%s/IPP/intel64:%s/IPP/intel64_legacy/:/usr/local/cuda/lib64";
	public String getgstLibraryPath(){return gstLibraryPath;}
	public static String getGstLibraryPath() {
		try{
			return String.format(MediaAMEServiceWorkerConfig.getInstance().getgstLibraryPath()
					, MediaAMEServiceWorkerConfig.getInstance().getgstWorkingDir()
					, MediaAMEServiceWorkerConfig.getInstance().getgstWorkingDir());
		}
		catch(Exception e){
			System.out.println("E:gstLibraryPath " + e.getMessage());
		}
//		return "LD_LIBRARY_PATH="+getGstWorkingDir()+"/IPP/intel64:"+getGstWorkingDir()+"/IPP/intel64_legacy/:/usr/local/cuda/lib64";
		return "";
	}


	private String videoSegmentationCmd;//="gst-launch-1.0 -v --gst-plugin-path=. filesrc location="+
//			/*inputFile +*/ "%s ! decodebin !  queue ! videoconvert ! jrsimageconverter ! tee name=imageTee ! queue ! jrshardcutdetector name=hardcutDet ! jrskeyframeextractor name=keyframeExt dataDirectory=\""+
//			/*outputDir +*/ "%s\" " + "! jrskeyframedescriptor name=keyframeDesc ! progressreport update-freq=1 ! filesink location=\"" +
//			/*kfMdFile +*/ "%s\" hardcutDet. ! application/jrs-image ! keyframeExt. hardcutDet. ! application/jrs-frame_description ! keyframeDesc. hardcutDet. ! jrstransitiondescriptor ! progressreport update-freq=1 ! filesink location=\""+
//			/*sbdMdFile +*/ "%s\"";
	public String getvideoSegmentationCmd() {return videoSegmentationCmd;}
	public static String getVideoSegmentationCmd(String inputFile, String outputDir, String sbdMdFile, String kfMdFile) {
		try{
			return String.format(MediaAMEServiceWorkerConfig.getInstance().getvideoSegmentationCmd(), inputFile, outputDir, kfMdFile, sbdMdFile);
		}
		catch(Exception e){
			System.out.println("E:videoSegmentationCmd " + e.getMessage());
		}

//		/*return "gst-launch-1.0 -v --gst-plugin-path=. filesrc location="+
//				inputFile + " ! qtdemux ! h264parse ! avdec_h264 ! queue ! videoconvert ! jrsimageconverter ! tee name=imageTee ! queue ! jrshardcutdetector name=hardcutDet ! jrskeyframeextractor name=keyframeExt dataDirectory=\""+
//				outputDir + "\" " + "! jrskeyframedescriptor name=keyframeDesc ! progressreport update-freq=1 ! filesink location=\"" + kfMdFile + "\" hardcutDet. ! application/jrs-image ! keyframeExt. hardcutDet. ! application/jrs-frame_description ! keyframeDesc. hardcutDet. ! jrstransitiondescriptor ! progressreport update-freq=1 ! filesink location=\""+
//				sbdMdFile + "\"";
//			*/
//
//		return "gst-launch-1.0 -v --gst-plugin-path=. filesrc location="+
//		inputFile + " ! decodebin !  queue ! videoconvert ! jrsimageconverter ! tee name=imageTee ! queue ! jrshardcutdetector name=hardcutDet ! jrskeyframeextractor name=keyframeExt dataDirectory=\""+
//		outputDir + "\" " + "! jrskeyframedescriptor name=keyframeDesc ! progressreport update-freq=1 ! filesink location=\"" + kfMdFile + "\" hardcutDet. ! application/jrs-image ! keyframeExt. hardcutDet. ! application/jrs-frame_description ! keyframeDesc. hardcutDet. ! jrstransitiondescriptor ! progressreport update-freq=1 ! filesink location=\""+
//		sbdMdFile + "\"";
		return "";
	}

	private String videoFaceCmd;//="gst-launch-1.0 --gst-plugin-path=./ filesrc -v location=" +
//			/*inputFile +*/ "%s ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
//			"name=imageconverter ! queue ! capsfilter caps=\"application/jrs-image, realtime=(bool)false, skipFrames=(int)5, max_buffer_size=(int)250\" ! "+
//			"jrsfacedetector dataDirectory=\""+
//			/*outputDir +*/ "%s\" ! filesink location=\"" +
//			/*faceMdFile +*/ "%s\"";
	public String getvideoFaceCmd() {return videoFaceCmd;}

	private String videoFaceObjectSleep;//=" && sleep 20s && ";
	public String getvideoFaceObjectSleep(){return videoFaceObjectSleep;
		// " && sleep 20s && ";
	}

	private String videoObjectCmd;//="gst-launch-1.0 --gst-plugin-path=./ filesrc -v location=" +
//			/*inputFile +*/ "%s ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
//			"name=imageconverter ! queue ! capsfilter caps=\"application/jrs-image, realtime=(bool)false, skipFrames=(int)10, max_buffer_size=(int)250\" "+
//			"! jrsyolodetector ! filesink location=\"" +
//			/*objMdFile +*/ "%s\"";
	public String getvideoObjectCmd(){return videoObjectCmd;}

	public static String getVideoAnalysisCmd(String inputFile, String outputDir, String faceMdFile, String objMdFile, boolean doFace, boolean doObject) {
		String cmd = "";
		try{
			if(doFace)
				cmd += String.format(MediaAMEServiceWorkerConfig.getInstance().getvideoFaceCmd(), inputFile, outputDir, faceMdFile);
			if(doFace && doObject)
				cmd += MediaAMEServiceWorkerConfig.getInstance().getvideoFaceObjectSleep();
			if(doObject)
				cmd += String.format(MediaAMEServiceWorkerConfig.getInstance().getvideoObjectCmd(), inputFile, objMdFile);
		}
		catch(Exception e){
			System.out.println("E:videoFaceCmd+videoFaceObjectSleep+videoObjectCmd " + e.getMessage());
		}

	/*
		if (doFace) cmd+= "gst-launch-1.0 --gst-plugin-path=./ filesrc -v location=" + inputFile + " ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
			   "name=imageconverter ! queue ! capsfilter caps=\"application/jrs-image, realtime=(bool)false, skipFrames=(int)5, max_buffer_size=(int)250\" ! "+
			   "jrsfacedetector dataDirectory=\""+outputDir + "\" ! filesink location=\"" + faceMdFile + "\"";

		if (doObject) cmd+=	"gst-launch-1.0 --gst-plugin-path=./ filesrc -v location=" + inputFile + " ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
			   "name=imageconverter ! queue ! capsfilter caps=\"application/jrs-image, realtime=(bool)false, skipFrames=(int)10, max_buffer_size=(int)250\" "+
			   "! jrsyolodetector ! filesink location=\"" + objMdFile + "\"";

	*/
		return cmd;
	}



	private String imageFaceCmd;//="gst-launch-1.0 --gst-plugin-path=./ filesrc -v location=" +
//		/*inputFile +*/ "%s ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
//		"name=imageconverter ! queue ! capsfilter caps=\"application/jrs-image, realtime=(bool)false, skipFrames=(int)1, max_buffer_size=(int)250\" ! "+
//		"jrsfacedetector dataDirectory=\""+
//		/*outputDir +*/ "%s\" ! filesink location=\"" +
//		/*faceMdFile +*/ "%s\"";
	public String getimageFaceCmd(){return imageFaceCmd;}

	private String imageFaceObjectSleep;//=" && sleep 20s && ";
	public String getimageFaceObjectSleep(){return imageFaceObjectSleep;
		// " && sleep 20s && ";
	}

	private String imageObjectCmd;//="gst-launch-1.0 --gst-plugin-path=./ filesrc -v location=" +
//			/*inputFile +*/ "%s ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
//			"name=imageconverter ! queue ! capsfilter caps=\"application/jrs-image, realtime=(bool)false, skipFrames=(int)0, max_buffer_size=(int)250\" "+
//			"! jrsyolodetector ! filesink location=\"" +
//			/*objMdFile +*/ "%s\"";
	public String getimageObjectCmd(){return imageObjectCmd;}

	public static String getImageAnalysisCmd(String inputFile, String outputDir, String faceMdFile, String objMdFile, boolean doFace, boolean doObject) {
		String cmd = "";
		try{
			if(doFace)
				cmd += String.format(MediaAMEServiceWorkerConfig.getInstance().getimageFaceCmd(), inputFile, outputDir, faceMdFile);
			if(doFace && doObject)
				cmd += MediaAMEServiceWorkerConfig.getInstance().getimageFaceObjectSleep();
			if(doObject)
				cmd += String.format(MediaAMEServiceWorkerConfig.getInstance().getimageObjectCmd(), inputFile, objMdFile);
		}
		catch(Exception e){
			System.out.println("E:imageFaceCmd+imageFaceObjectSleep+imageObjectCmd " + e.getMessage());
		}
		// not sure if animated GIFs wor with decodebin, may need to use
		// gst-launch-1.0 filesrc location=demo.gif ! avdemux_gif ! avdec_gif ! autovideosink

/*
		if (doFace) cmd+= "gst-launch-1.0 --gst-plugin-path=./ filesrc -v location=" + inputFile + " ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
				   "name=imageconverter ! queue ! capsfilter caps=\"application/jrs-image, realtime=(bool)false, skipFrames=(int)1, max_buffer_size=(int)250\" ! "+
				   "jrsfacedetector dataDirectory=\""+outputDir + "\" ! filesink location=\"" + faceMdFile + "\"";

		if (doObject) cmd+= "gst-launch-1.0 --gst-plugin-path=./ filesrc -v location=" + inputFile + " ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
				   "name=imageconverter ! queue ! capsfilter caps=\"application/jrs-image, realtime=(bool)false, skipFrames=(int)0, max_buffer_size=(int)250\" "+
				   "! jrsyolodetector ! filesink location=\"" + objMdFile + "\"";
*/
		return cmd;
	}


	private String visualQualityAnalysisCmd;//="gst-launch-1.0 --gst-plugin-path=./ filesrc -v location="+
//			/*inputFile+*/ "%s ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
//			" ! tee name=imageTee imageTee. ! queue ! jrsnoiselevel ! jrsdqdescriptor name=desc ! filesink location="+
//			"\""+
//			/*vqFile+*/"%s\" imageTee. ! queue ! jrssharpnessextraction ! desc. imageTee. ! queue ! jrsmacroblocking ! desc.";
	public String getvisualQualityAnalysisCmd() {return visualQualityAnalysisCmd;}
	public static String getVisualQualityAnalysisCmd(String inputFile, String outputDir, String vqFile) {
		try{
			return String.format(MediaAMEServiceWorkerConfig.getInstance().getvisualQualityAnalysisCmd(), inputFile, vqFile);
		}
		catch(Exception e){
			System.out.println("E:visualQualityAnalysisCmd " + e.getMessage());
		}

/*
		return "gst-launch-1.0 --gst-plugin-path=./ filesrc -v location="+inputFile+" ! decodebin ! videoconvert ! queue ! jrsimageconverter "+
	           " ! tee name=imageTee imageTee. ! queue ! jrsnoiselevel ! jrsdqdescriptor name=desc ! filesink location="+
				"\""+vqFile+"\" imageTee. ! queue ! jrssharpnessextraction ! desc. imageTee. ! queue ! jrsmacroblocking ! desc.";
*/
		return "";
	}

	private String targetBaseUrlGPU;//="http://34.255.173.12:8080/data";
	public String getTargetBaseUrlGPU(){return targetBaseUrlGPU;}

	private String targetBaseUrl;//="http://34.247.138.24:8080/data";
	public String gettargetBaseUrl() { return gpuHost? targetBaseUrlGPU : targetBaseUrl;}
	public static String getTargetBaseUrl() {
		return MediaAMEServiceWorkerConfig.getInstance().gettargetBaseUrl();
//		if (gpuHost) return "http://34.255.173.12:8080/data";
//		else return "http://34.247.138.24:8080/data";
//		//return "http://localhost:8080/fims2/data";
	}
}
