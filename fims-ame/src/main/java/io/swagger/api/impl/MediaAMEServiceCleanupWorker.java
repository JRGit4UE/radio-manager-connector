package io.swagger.api.impl;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import io.swagger.api.impl.MediaAMEServiceWorker.StatusEnum;
import io.swagger.model.AmeJobType;

public class MediaAMEServiceCleanupWorker extends Thread {
	
	protected boolean running = false;
	
	public MediaAMEServiceCleanupWorker() {
	}

	public boolean isRunning() { return running; }
	
	public void run() {
		running = true;
		
		while (true) {
			Date now = new Date();
			
			List<AmeJobType> jobList = MediaAMEServiceJobRegistry.getFinishedCancelledFailedJobs();
			
			for (AmeJobType job : jobList) {
			    long diff = Math.abs(now.getTime()-job.getResourceModifiedDate().getTime());
				if (diff > 24*3600*1000) {
					String jobIdStr = job.getResourceID();							
					String targetFolder = MediaAMEServiceWorkerConfig.getTargetRootFolder() + "/" + jobIdStr;
					
					try {
						FileUtils.deleteDirectory(new File(targetFolder));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					MediaAMEServiceJobRegistry.deleteJob(Integer.parseInt(job.getResourceID()));
				}
			}
			
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
		

	
}
