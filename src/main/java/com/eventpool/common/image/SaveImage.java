package com.eventpool.common.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.CRC32;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaveImage {

	private static final String FILE_PREFIX = "file:///";

	protected static final Logger logger = LoggerFactory.getLogger(SaveImage.class);
	
	@Resource
	private ImageProcessor imageProcessor;

	@Value("$EVENT_POOL{image.location.prefix}")
	private String imageBasePath ;//= "C://Event//image//";
	
	@Value("$EVENT_POOL{image.location}")
	private String imageBasePathForDb;// = "eventpool//images//";
	
	@Value("$EVENT_POOL{image.source.location}")
	private String localImagePath ;//= "C://Event//source";
	
	private final int BANNER_WIDTH = 1000;
	private final int BANNER_HEIGHT = 300;

	private final int PROMO_WIDTH = 228;
	private final int PROMO_HEIGHT = 275;

	private Map<ImageType,Integer> widthMap = new HashMap<ImageType, Integer>();
	private Map<ImageType,Integer> heightMap = new HashMap<ImageType, Integer>();
	
	@PostConstruct
	public void init(){
		
		widthMap.put(ImageType.BANNER, BANNER_WIDTH);
		widthMap.put(ImageType.PROMO, PROMO_WIDTH);

		heightMap.put(ImageType.BANNER, BANNER_HEIGHT);
		heightMap.put(ImageType.PROMO, PROMO_HEIGHT);

	}
	public Map<ImageType,String> saveImageOnDisk(String imageUrl,ImageType imageType) throws Exception{
		if(imageUrl==null) return null;
		if(!imageUrl.startsWith("http")){
			imageUrl =FILE_PREFIX+localImagePath+"/"+imageUrl;
		}
		Map<ImageType,String> processedImageMap = new HashMap<ImageType, String>();
		BufferedImage sourceImage = imageProcessor.getSourceImage(imageUrl);
		
		BufferedImage scaledImage = imageProcessor.scaleTo(sourceImage, widthMap.get(imageType), heightMap.get(imageType));
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String fileName = getFileName(widthMap.get(imageType), heightMap.get(imageType), uuid);
		String dbfileName = getDbfileName(imageUrl, fileName);
		saveOnDisk(scaledImage, imageUrl, fileName, .9f);
		processedImageMap.put(imageType, dbfileName);
		
		return processedImageMap;
	}
	
	protected String replaceLast(String input,String appendText){
		if(input!=null && !input.isEmpty()){
			int lastIndexOf = input.lastIndexOf(".");
			if(lastIndexOf!=-1){
				return input.substring(0, lastIndexOf)+appendText+input.substring(lastIndexOf);
			}
		}
		return null;
	}
	protected static String getHashPath(String imgPath, int maxNumOfFolders){
	   if(imgPath == null)
	       return null;
	   if(maxNumOfFolders == 0)
	       return "";
	   CRC32 x = new CRC32();
	   x.update(imgPath.getBytes());
	   String outPutImageDir = (1+ x.getValue()%maxNumOfFolders) + "/";
	   return outPutImageDir;
	 }
	
	protected boolean createDir(String dirPath) {
		boolean dirExistsOrCreated = false;
		File f = new File(dirPath);
		if (!f.exists()) {
			dirExistsOrCreated = f.mkdirs();
		}
		else
			dirExistsOrCreated = true;
		return dirExistsOrCreated;
	}
	
	protected static String md5(String key) {
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
	
	protected String getDbfileName(String imageUrl, String fileName) {
		return imageBasePathForDb+getHashPath(imageUrl, 1000)+fileName;
	}

	protected  void saveOnDisk(BufferedImage scaledImage, String imageUrl , String fileName, float imageQuality) throws Exception {
		String dirName = imageBasePath+imageBasePathForDb+getHashPath(imageUrl, 1000);
		String absoluteFileName=dirName+fileName;
		// create directory.
		if ( createDir(dirName)){ 
			ImageProcessor.writeImageToDisk(scaledImage,absoluteFileName, imageQuality);
		}else{
			logger.error("could not create directory : "+dirName);
		}
	}

	protected String getFileName( Integer width,Integer height,String uuid) {
		if(width==null) width=0;
		if(height==null) height=0;
		return width+"X"+height+"_"+uuid+".jpg";
	}

	public String saveInSourceLocation(File file) throws IOException{
		BufferedImage sourceImage = imageProcessor.getSourceImage(file);
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String destFile = uuid+".jpg";
		ImageProcessor.writeImageToDisk(sourceImage,localImagePath+"/"+destFile, 0.9f);
		return destFile;
	}
	
	public String saveInSourceLocation(InputStream is) throws IOException{
		BufferedImage sourceImage = imageProcessor.getSourceImage(is);
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String destFile = uuid+".jpg";
		ImageProcessor.writeImageToDisk(sourceImage,localImagePath+"/"+destFile, 0.9f);
		return destFile;
	}
}
