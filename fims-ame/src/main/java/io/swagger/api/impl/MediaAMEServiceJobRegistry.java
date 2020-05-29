package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;
import java.util.*;
import java.math.BigDecimal;


public class MediaAMEServiceJobRegistry {

	protected static HashMap<Integer,AmeJobType> jobMap = new HashMap<Integer,AmeJobType>();
	protected static int lastJobId = 0;

	protected static HashMap<Integer,MediaAMEServiceWorker> workerMap = new HashMap<Integer,MediaAMEServiceWorker>();
	
	protected static MediaAMEServiceCleanupWorker cleanupWorker = new MediaAMEServiceCleanupWorker();
	
	public static int addJob(AmeJobType job) {

		lastJobId++;
		
		if (job.getResourceID()==null) job.setResourceID(new Integer(lastJobId).toString());
		if (job.getRevisionID()==null) job.setRevisionID("1");
		if (job.getResourceCreationDate()==null) job.setResourceCreationDate(new Date());
		if (job.getResourceModifiedDate()==null) job.setResourceModifiedDate(new Date());
		
		if (job.getPriority()==null) job.setPriority(AmeJobType.PriorityEnum.MEDIUM);
		if (job.getProcessed()==null) {
			AmeJobTypeProcessed processed = new AmeJobTypeProcessed();
			processed.setPercentageProcessedCompleted(new BigDecimal(0));
			job.setProcessed(processed);
		}
		
		jobMap.put(new Integer(lastJobId),job);
		
		workerMap.put(new Integer(lastJobId),new MediaAMEServiceWorker(job));
		
		if (!cleanupWorker.isRunning()) cleanupWorker.start();
		
		return lastJobId;
	}
	
	public static AmeJobType getJob(int id) {
		AmeJobType job = jobMap.get(new Integer(id));
		return job; 
	}
	
	public static MediaAMEServiceWorker getWorker(int id) {
		MediaAMEServiceWorker worker = workerMap.get(new Integer(id));
		return worker; 
	}
	
	public static void deleteJob(int id) {
		jobMap.remove(new Integer(id));
		workerMap.remove(new Integer(id));
	}
	
	public static List<AmeJobType> getFinishedCancelledFailedJobs() {
		List<AmeJobType> resultList = new ArrayList<AmeJobType>();
		
		for (Integer id : jobMap.keySet()){
			AmeJobType job = jobMap.get(id);
			if (job.getStatus()==AmeJobType.StatusEnum.COMPLETED || 
			    job.getStatus()==AmeJobType.StatusEnum.FAILED ||
			    job.getStatus()==AmeJobType.StatusEnum.CANCELED) {
				resultList.add(job);
			}
		}
		
		return resultList;
	}
	
	public static void updateStatus(int id, AmeJobType.StatusEnum status) {
		jobMap.get(new Integer(id)).setStatus(status);
		
		// TODO update worker - not supported right now
		
	}
	
	public static String serialise() {
		String s = "[";
		boolean first = true;
		for (Map.Entry<Integer, AmeJobType> entry : jobMap.entrySet())
		{
		    if (!first) {
		    	s = s+ ",";
		    }
		    else first =false;
			s= s + entry.getValue().toString();
		}
		s = s + "]";
		return s;
	}
	
	
}
