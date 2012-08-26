package com.team06.roadangel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.team06.roadangel.model.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public class Deserializer {

    public static final String KEY_ALERTS = "alerts";
    public static final String KEY_ALERT = "alert";
	public static final String KEY_FROM = "fromKey";
	public static final String KEY_LICENSE_PLATE = "licensePlate";
	public static final String KEY_STATE = "state";
	public static final String KEY_REASON = "reason";
	public static final String KEY_TIME = "time";

	public static List<Alert> parseAlerts(String xmlData) {

		List<Alert> alerts = new ArrayList<Alert>();

		if(xmlData.length() == 0) {
			return alerts;
		}

		XMLParser parser = new XMLParser();
		Document doc = parser.getDomElement(xmlData);

		NodeList alertsNodes = doc.getElementsByTagName(KEY_ALERTS);

        for(int i = 0; i < alertsNodes.getLength(); i++) {
            NodeList alertNodes = alertsNodes.item(i).getChildNodes();

            for (int j = 0; j < alertNodes.getLength(); j++) {

                Element alertElement = (Element) alertNodes.item(i);

                Alert alert = new Alert();

                alert.setFromKey(parser.getValue(alertElement, KEY_FROM));
                alert.setLicensePlate(parser.getValue(alertElement, KEY_LICENSE_PLATE));
                alert.setState(parser.getValue(alertElement, KEY_STATE));
                alert.setReason(parser.getValue(alertElement, KEY_REASON));
                alerts.add(alert);
            }
        }

		return alerts;
	}

	public static String loadFile(File cacheDir, String filename) {
		// Get our data dir
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"ImageSlides");
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
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"ImageSlides");
        }

		FileOutputStream fos = new FileOutputStream(new File(cacheDir, filename));
		fos.write(xml.getBytes());
		fos.flush();
		fos.close();
	}
}
