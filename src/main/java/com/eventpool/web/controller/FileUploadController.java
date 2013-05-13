package com.eventpool.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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

	@RequestMapping(value = "/file", method = RequestMethod.POST)
    public @ResponseBody
    UploadedFileResponse setProfilePic(@RequestParam("files[]") MultipartFile fileupload) {
    	UploadedFileResponse response = new UploadedFileResponse();
    	response.setStatus(true);
//    	System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + 
    	File file = new File(fileupload.getOriginalFilename());
    	String saveInSourceLocation = "";
    	try {
			fileupload.transferTo(file);
			saveInSourceLocation = save.saveInSourceLocation(file);
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
