package com.eventpool.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.mime.content.FileBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.eventpool.common.image.ImageProcessor;
import com.eventpool.common.image.SaveImage;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.web.domain.PhotoWeb;
import com.eventpool.web.domain.UploadedFileResponse;


@Controller
@RequestMapping("/upload")
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Resource
	SaveImage save;
	
	private final int BANNER_MIN_WIDTH = 990;
	private final int BANNER_MIN_HEIGHTH = 285;
	
	private final int PROMOTION_MIN_WIDTH = 240;
	private final int PROMOTION_MIN_HEIGHTH = 135;

	@Resource
	ImageProcessor imageProcessor;
	
	@RequestMapping(value = "/banner", method = RequestMethod.POST)
    public @ResponseBody
    UploadedFileResponse uploadBanner(@RequestParam("file") MultipartFile fileupload) {
    	UploadedFileResponse response = new UploadedFileResponse();
    	response.setStatus(true);
//    	System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + 
    	File file = new File(fileupload.getOriginalFilename());
    	String saveInSourceLocation = "";
    	try {
			//fileupload.transferTo(file);
    		String error = null;
    		BufferedImage bannerImage = imageProcessor.getSourceImage(fileupload.getInputStream());
    		int width = bannerImage.getWidth();
    		int height = bannerImage.getHeight();
    		
    		if(BANNER_MIN_HEIGHTH > height){
    			response.setStatus(false);
    			error  = "minimum heightXwidth : "+BANNER_MIN_HEIGHTH+"X"+BANNER_MIN_WIDTH;
    		}
    		if(BANNER_MIN_WIDTH > width){
    			response.setStatus(false);
    			error  = "minimum heightXwidth : "+BANNER_MIN_HEIGHTH+"X"+BANNER_MIN_WIDTH;
    		}
    		response.setError(error);
    		if(error==null)
			saveInSourceLocation = save.saveInSourceLocation(fileupload.getInputStream());
    		response.setName(fileupload.getOriginalFilename());
		}catch (IllegalStateException e) {
			response.setStatus(false);
			logger.info("File uploaded error:",e);
		} catch (IOException e) {
			response.setStatus(false);
			logger.info("File uploaded error:",e);
		}
    	
    	List<PhotoWeb> photosData = new ArrayList<PhotoWeb>();
    	PhotoWeb photo = new PhotoWeb();
    	photo.setUniqueid(saveInSourceLocation);
    	photosData.add(photo);
    	
    	response.setFilesuploaded(photosData);
    	System.out.println("File uploaded :"+saveInSourceLocation);
    	return response;
    }
	
	@RequestMapping(value = "/promotion", method = RequestMethod.POST)
    public @ResponseBody
    UploadedFileResponse uploadPromotion(@RequestParam("file") MultipartFile fileupload) {
    	UploadedFileResponse response = new UploadedFileResponse();
    	response.setStatus(true);
//    	System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + 
    	File file = new File(fileupload.getOriginalFilename());
    	String saveInSourceLocation = "";
    	try {
			//fileupload.transferTo(file);
    		String error = null;
    		BufferedImage bannerImage = imageProcessor.getSourceImage(fileupload.getInputStream());
    		int width = bannerImage.getWidth();
    		int height = bannerImage.getHeight();
    		
    		if(PROMOTION_MIN_HEIGHTH > height){
    			response.setStatus(false);
    			error  = "minimum heightXwidth : "+PROMOTION_MIN_HEIGHTH+"X"+PROMOTION_MIN_WIDTH;
    		}
    		if(PROMOTION_MIN_WIDTH > width){
    			response.setStatus(false);
    			error  = "minimum heightXwidth : "+PROMOTION_MIN_HEIGHTH+"X"+PROMOTION_MIN_WIDTH;
    		}
    		response.setError(error);
    		if(error==null)
			saveInSourceLocation = save.saveInSourceLocation(fileupload.getInputStream());
		} catch (IllegalStateException e) {
			response.setStatus(false);
			logger.info("File uploaded error:",e);
		} catch (IOException e) {
			response.setStatus(false);
			logger.info("File uploaded error:",e);
		}
    	
    	List<PhotoWeb> photosData = new ArrayList<PhotoWeb>();
    	PhotoWeb photo = new PhotoWeb();
    	photo.setUniqueid(saveInSourceLocation);
    	photosData.add(photo);
    	
    	response.setFilesuploaded(photosData);
    	logger.info("File uploaded :"+saveInSourceLocation);
    	return response;
    }
	
	
	@RequestMapping(value = "/image", method = RequestMethod.POST)
    public @ResponseBody
    UploadedFileResponse uploadFormData(HttpServletRequest request) {
		
		DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = (DefaultMultipartHttpServletRequest)request;
		MultipartFile file = defaultMultipartHttpServletRequest.getFile("banner");
    	UploadedFileResponse response = new UploadedFileResponse();
    	response.setStatus(true);
    	String saveInSourceLocation = "";
    	try {
			//fileupload.transferTo(file);
    		String error = null;
    		BufferedImage bannerImage = imageProcessor.getSourceImage(file.getInputStream());
    		int width = bannerImage.getWidth();
    		int height = bannerImage.getHeight();
    		
    		if(BANNER_MIN_HEIGHTH > height){
    			response.setStatus(false);
    			error  = "minimum heightXwidth : "+BANNER_MIN_HEIGHTH+"X"+BANNER_MIN_WIDTH;
    		}
    		if(BANNER_MIN_WIDTH > width){
    			response.setStatus(false);
    			error  = "minimum heightXwidth : "+BANNER_MIN_HEIGHTH+"X"+BANNER_MIN_WIDTH;
    		}
    		response.setError(error+" for uploading banner image");
    		if(error==null){
    			saveInSourceLocation = save.saveInSourceLocation(bannerImage);
    		}
		}catch (IllegalStateException e) {
			response.setStatus(false);
			response.setError("unable to upload. Please try new image.");
			logger.info("File uploaded error:",e);
		} catch (IOException e) {
			response.setStatus(false);
			response.setError("unable to upload. Please try new image.");
			logger.info("File uploaded error:",e);
		}
    	
    	List<PhotoWeb> photosData = new ArrayList<PhotoWeb>();
    	PhotoWeb photo = new PhotoWeb();
    	photo.setUniqueid(saveInSourceLocation);
    	photosData.add(photo);
    	
    	response.setFilesuploaded(photosData);
    	logger.info("File uploaded :"+saveInSourceLocation);
    	return response;
    }

	
	@RequestMapping(value = "/promo", method = RequestMethod.POST)
    public @ResponseBody
    UploadedFileResponse uploadPromotion(HttpServletRequest request) {
		DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = (DefaultMultipartHttpServletRequest)request;
		MultipartFile file = defaultMultipartHttpServletRequest.getFile("promotion");
		UploadedFileResponse response = new UploadedFileResponse();
    	response.setStatus(true);
    	String saveInSourceLocation = "";
    	try {
			//fileupload.transferTo(file);
    		String error = null;
    		BufferedImage bannerImage = imageProcessor.getSourceImage(file.getInputStream());
    		//BufferedImage bannerImage = ImageIO.read(file.getInputStream());
    		int width = bannerImage.getWidth();
    		int height = bannerImage.getHeight();
    		
    		if(PROMOTION_MIN_HEIGHTH > height){
    			response.setStatus(false);
    			error  = "minimum heightXwidth : "+PROMOTION_MIN_HEIGHTH+"X"+PROMOTION_MIN_WIDTH;
    		}
    		if(PROMOTION_MIN_WIDTH > width){
    			response.setStatus(false);
    			error  = "minimum heightXwidth : "+PROMOTION_MIN_HEIGHTH+"X"+PROMOTION_MIN_WIDTH;
    		}
    		response.setError(error+" for uploading promo image");
    		if(error==null)
			saveInSourceLocation = save.saveInSourceLocation(bannerImage);
		} catch (IllegalStateException e) {
			response.setStatus(false);
			response.setError("unable to upload. Please try new image.");
			logger.info("File uploaded error:",e);
		} catch (IOException e) {
			response.setStatus(false);
			response.setError("unable to upload. Please try new image.");
			logger.info("File uploaded error:",e);
		}
    	List<PhotoWeb> photosData = new ArrayList<PhotoWeb>();
    	PhotoWeb photo = new PhotoWeb();
    	photo.setUniqueid(saveInSourceLocation);
    	photosData.add(photo);
    	
    	response.setFilesuploaded(photosData);
    	logger.info("File uploaded :"+saveInSourceLocation);
    	return response;
    }

}
