package com.team06.roadangel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public class Deserializer {

//	public static final String KEY_CATEGORY = "category";
//	public static final String KEY_CATEGORYNAME = "name";
//	public static final String KEY_CATEGORYID = "id";
//
//	public static final String KEY_GALLERY = "gallery";
//	public static final String KEY_ID = "id";
//	public static final String KEY_TITLE = "title";
//	public static final String KEY_WEBSITE = "url";
//	public static final String KEY_NUMPHOTOS = "numPhotos";
//	public static final String KEY_THUMB_URL = "thumbnail";
//
//	public static List<Category> parseCategories(String xmlData) {
//
//		List<Category> categories = new ArrayList<Category>();
//
//		if(xmlData.length() == 0) {
//			return categories;
//		}
//
//		XMLParser parser = new XMLParser();
//		Document doc = parser.getDomElement(xmlData);
//
//		NodeList categoryNodes = doc.getElementsByTagName(KEY_CATEGORY);
//
//		for (int i = 0; i < categoryNodes.getLength(); i++) {
//			Element categoryElement = (Element) categoryNodes.item(i);
//
//			Category category = new Category();
//			categories.add(category);
//
//			category.setName(parser.getValue(categoryElement, KEY_CATEGORYNAME));
//			category.setId(Integer.decode(parser.getValue(categoryElement, KEY_CATEGORYID)));
//			List<Gallery> galleries = new ArrayList<Gallery>();
//			category.setGalleries(galleries);
//
//			// Get the galleries within this category
//			NodeList galleryNodes = categoryElement.getElementsByTagName(KEY_GALLERY);
//			for(int j = 0; j < galleryNodes.getLength(); j++) {
//				Element e = (Element) galleryNodes.item(j);
//
//				// Load our gallery data
//				Gallery gallery = new Gallery();
//
//				gallery.setId(Integer.decode(parser.getValue(e, KEY_ID)));
//				String title = parser.getValue(e, KEY_TITLE);
//				gallery.setTitle(parser.getValue(e, KEY_TITLE));
//				gallery.setThumbnail(parser.getValue(e, KEY_THUMB_URL));
//				gallery.setNumPhotos(Integer.decode(parser.getValue(e, KEY_NUMPHOTOS)));
//				gallery.setWebsite(parser.getValue(e, KEY_WEBSITE));
//
//				galleries.add(gallery);
//			}
//		}
//
//		return categories;
//	}
//
//	public static String loadFile(File cacheDir, String filename) {
//		// Get our data dir
//        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"ImageSlides");
//        }
//
//        boolean doneLoading = false;
//        String xml = "";
//
//        // Lets see if we have a init data file
//        if(cacheDir.exists()) {
//        	File initFile = new File(cacheDir, filename);
//
//        	if(initFile.exists()) {
//        		try {
//        			// Load it up
//        			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(initFile)));
//        			String temp;
//
//        			while((temp = r.readLine()) != null) {
//        				xml += temp; // + "\r\n";
//        			}
//        			r.close();
//        			doneLoading = true;
//        		}
//        		catch(IOException e) {}
//        	}
//        }
//        else {
//        	cacheDir.mkdir();
//        }
//
//        if(doneLoading) {
//        	return xml;
//        }
//        else {
//        	return null;
//        }
//	}
//
//	public static void write(File cacheDir, String filename, String xml) throws IOException {
//		// Get our data dir
//        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"ImageSlides");
//        }
//
//		FileOutputStream fos = new FileOutputStream(new File(cacheDir, filename));
//		fos.write(xml.getBytes());
//		fos.flush();
//		fos.close();
//	}
}
