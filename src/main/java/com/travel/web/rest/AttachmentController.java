package com.travel.web.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travel.model.Attachment;
import com.travel.repositories.AttachmentRepository;

@RestController
@RequestMapping("attachment")
public class AttachmentController {
	
	@Autowired
	private AttachmentRepository attachmentRepo;
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void upload(@RequestParam("file") List<MultipartFile> files, HttpServletRequest request,HttpServletResponse response) throws IOException {
		System.out.println("Inside the controller!!!!");
		if(files==null || files.size()==0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		Attachment attachment=null;
		for(MultipartFile multipartFile: files) {
			attachment=new Attachment();
			final String fileName=multipartFile.getOriginalFilename();
			final long size=multipartFile.getSize();
			final String contentType=multipartFile.getContentType();
			final InputStream inputStream=multipartFile.getInputStream();
			attachment.setFileName(fileName);
			attachment.setContentType(contentType);
			attachment.setSize(size);
			Attachment save = attachmentRepo.save(attachment);
			String filePath=System.getProperty("user.dir")+"\\dev\\data\\pics\\"+attachment.getId()+"_"+fileName;
			File dest=new File(filePath);
			multipartFile.transferTo(dest);
		}	
	}
}
