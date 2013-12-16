package com.eventpool.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eventpool.common.image.SaveImage;
import com.eventpool.web.domain.PhotoWeb;
import com.eventpool.web.domain.UploadedFileResponse;


@Controller
@RequestMapping("/upload")
public class FileUploadController {
	
	@Resource
	SaveImage save;
	
	private final int BANNER_MIN_WIDTH = 990;
	private final int BANNER_MIN_HEIGHTH = 285;
	
	private final int PROMOTION_MIN_WIDTH = 240;
	private final int PROMOTION_MIN_HEIGHTH = 135;

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
    		BufferedImage bannerImage = ImageIO.read(fileupload.getInputStream());
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
		} catch (IllegalStateException e) {
			response.setStatus(false);
			e.printStackTrace();
		} catch (IOException e) {
			response.setStatus(false);
			e.printStackTrace();
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
    		BufferedImage bannerImage = ImageIO.read(fileupload.getInputStream());
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
			e.printStackTrace();
		} catch (IOException e) {
			response.setStatus(false);
			e.printStackTrace();
		}
    	
    	List<PhotoWeb> photosData = new ArrayList<PhotoWeb>();
    	PhotoWeb photo = new PhotoWeb();
    	photo.setUniqueid(saveInSourceLocation);
    	photosData.add(photo);
    	
    	response.setFilesuploaded(photosData);
    	System.out.println("File uploaded :"+saveInSourceLocation);
    	return response;
    }
}
