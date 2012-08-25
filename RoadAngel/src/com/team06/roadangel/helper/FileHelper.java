package com.team06.roadangel.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {

	public static String loadFile(File cacheDir, String filename) {
	
		// Get our data dir
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"RoadAngel");
        }
        
        boolean doneLoading = false;
        String xml = "";
        
        // Lets see if we have a init data file
        if(cacheDir.exists()) {
        	File initFile = new File(cacheDir, filename);
        	
        	if(initFile.exists()) {
        		try {
        			// Load it up
        			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(initFile)));
        			String temp;
        			
        			while((temp = r.readLine()) != null) {
        				xml += temp; // + "\r\n";
        			}
        			r.close();
        			doneLoading = true;
        		}
        		catch(IOException e) {}
        	}
        }
        else {
        	cacheDir.mkdir();
        }
        
        if(doneLoading) {
        	return xml;
        }
        else {
        	return null;
        }
	}
	
	public static void write(File cacheDir, String filename, String xml) throws IOException {
		// Get our data dir
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"RoadAngel");
        }
		
		FileOutputStream fos = new FileOutputStream(new File(cacheDir, filename));
		fos.write(xml.getBytes());
		fos.flush();
		fos.close();
	}
	
}
