
package io.swagger.model;
// FTT

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.net.*;
import io.swagger.api.impl.MediaAMEServiceWorkerConfig;

public class ModelUpdateThread extends Thread {

    private static final Object lock = new Object();
    private List<String> files = null;

    public ModelUpdateThread(){ files=null; }
    public ModelUpdateThread(List <String> f){files = f;}
    public void setData(List <String> f){files = f;}

    public void run(){
        System.out.println("Before synchronised doUpdate() " + Thread.currentThread().getId());
        synchronized(lock) {
            if(files == null || files.size() != 2) {
                ModelUpdateState.getInstance().setState(ModelUpdateStates.Failed);
                return;
            }
            List<String> tmpCopy = files;
            ModelUpdateState.getInstance().setState(ModelUpdateStates.InProgress);

            try {
                // TODO adjust MediaAMEServiceWorkerConfig with model update dir
                // Model update destination currently hard-coded
                String srcFaceDBFilename = "unknown";
                String dstFaceDBFilename = MediaAMEServiceWorkerConfig.getInstance().getgstWorkingDirGPU() + "/Classifier/new/Classifier.orf";
                String srcClassifierFilename = "unknonwn";
                String dstClassifierFilename = MediaAMEServiceWorkerConfig.getInstance().getgstWorkingDirGPU() + "/Classifier/new/Classifier.h5";
                if(tmpCopy.get(0).toLowerCase().endsWith(".orf")) {
                    srcFaceDBFilename = tmpCopy.get(0);
                    srcClassifierFilename = tmpCopy.get(1);
                }
                else {
                    srcFaceDBFilename = tmpCopy.get(1);
                    srcClassifierFilename = tmpCopy.get(0);
                }

                if(Thread.interrupted()) {
                    System.out.println("Model update interrupted, no file updated");
                    ModelUpdateState.getInstance().setState(ModelUpdateStates.Ok);
                    return;
                }

                System.out.println(String.format("Copy file '%s' to local dir ",srcClassifierFilename));
                InputStream inputStream = new URL(srcClassifierFilename).openStream();
                Files.copy(inputStream, Paths.get(dstClassifierFilename), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Classifier file copied");
                inputStream.close();

                if(Thread.interrupted()) {
                    System.out.println("ERROR Model update interrupted, one file updated - incomplete status");
                    ModelUpdateState.getInstance().setState(ModelUpdateStates.Failed);
                    return;
                }

                System.out.println(String.format("Copy file '%s' to local dir ",srcFaceDBFilename));
                inputStream = new URL(srcFaceDBFilename).openStream();
                Files.copy(inputStream, Paths.get(dstFaceDBFilename), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Face db file copied");
                inputStream.close();

                ModelUpdateState.getInstance().setState(ModelUpdateStates.Ok);
            }
            catch(Exception e){
                System.out.println(String.format("Exception at model update: %s", e.toString()));
                ModelUpdateState.getInstance().setState(ModelUpdateStates.Failed);
            }
        }
    }
}

