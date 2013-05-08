package com.eventpool.common.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.CRC32;

import javax.annotation.Resource;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaveImage {

	protected static final Logger logger = LoggerFactory.getLogger(SaveImage.class);
	
	@Resource
	private ImageProcessor imageProcessor;

	//@Value("${image-base-path}")
	private String imageBasePath;
	
	//@Value("${image-base-path-for-db-entry}")
	private String imageBasePathForDb;

	public Map<ImageType,String> saveImageOnDisk(String imageUrl, float imageQuality) throws Exception{
		Map<ImageType,String> processedImageMap = new HashMap<ImageType, String>();
		int width = 300;
		int height = 300;
		
		BufferedImage sourceImage = imageProcessor.getSourceImage(imageUrl);
		BufferedImage scaledImage = imageProcessor.scaleTo(sourceImage, width, height);
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String fileName = getFileName(width, height, uuid);
		String dbfileName = getDbfileName(imageUrl, fileName);
		saveOnDisk(scaledImage, imageUrl, dbfileName, imageQuality);
		processedImageMap.put(ImageType.MEDIUM, dbfileName);
		
		return processedImageMap;
	}
	
	public String replaceLast(String input,String appendText){
		if(input!=null && !input.isEmpty()){
			int lastIndexOf = input.lastIndexOf(".");
			if(lastIndexOf!=-1){
				return input.substring(0, lastIndexOf)+appendText+input.substring(lastIndexOf);
			}
		}
		return null;
	}
	public static String getHashPath(String imgPath, int maxNumOfFolders){
	   if(imgPath == null)
	       return null;
	   if(maxNumOfFolders == 0)
	       return "";
	   CRC32 x = new CRC32();
	   x.update(imgPath.getBytes());
	   String outPutImageDir = (1+ x.getValue()%maxNumOfFolders) + "/";
	   return outPutImageDir;
	 }
	
	public boolean createDir(String dirPath) {
		boolean dirExistsOrCreated = false;
		File f = new File(dirPath);
		if (!f.exists()) {
			dirExistsOrCreated = f.mkdirs();
		}
		else
			dirExistsOrCreated = true;
		return dirExistsOrCreated;
	}
	
	public static String md5(String key) {
		 MessageDigest m;
		 try {
		     m = MessageDigest.getInstance("MD5");
		     m.update(key.getBytes(), 0, key.length());
		     String hashvalue = new BigInteger(1, m.digest()).toString(16);
		     while (hashvalue.length() < 32) {
    		     hashvalue = "0" + hashvalue;
		     }
		     return hashvalue;
	     } catch (NoSuchAlgorithmException e) {
	    	 logger.error("No such algorithm exception",e);
	     }
		 return "";
    }
	
	public String getDbfileName(String imageUrl, String fileName) {
		return imageBasePathForDb+getHashPath(imageUrl, 1000)+fileName;
	}

	public  void saveOnDisk(BufferedImage scaledImage, String imageUrl , String fileName, float imageQuality) throws Exception {
		String dirName = imageBasePath+getHashPath(imageUrl, 1000);
		String absoluteFileName=dirName+fileName;
		// create directory.
		if ( createDir(dirName)){ 
			ImageProcessor.writeImageToDisk(scaledImage,absoluteFileName, imageQuality);
		}else{
			logger.error("could not create directory : "+dirName);
		}
	}

	public String getFileName( Integer width,Integer height,String uuid) {
		if(width==null) width=0;
		if(height==null) height=0;
		return width+"X"+height+"_"+uuid+".jpg";
	}

}
