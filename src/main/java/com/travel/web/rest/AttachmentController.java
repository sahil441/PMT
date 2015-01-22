package com.travel.web.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@ResponseBody
	public List<Object> upload(@RequestParam("file") List<MultipartFile> files, HttpServletRequest request,HttpServletResponse response) throws IOException {
		if(files==null || files.size()==0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		List<Object> json=new ArrayList<Object>();
		Map<String,Object> map;
		for(MultipartFile multipartFile: files) {
			 map=new HashMap<String, Object>();
			Attachment attachment=new Attachment();
			final String fileName=multipartFile.getOriginalFilename();
			final long size=multipartFile.getSize();
			final String contentType=multipartFile.getContentType();
			attachment.setFileName(fileName);
			attachment.setContentType(contentType);
			attachment.setSize(size);
			attachment=attachmentRepo.save(attachment);
			map.put("id", attachment.getId());
			map.put("fileName", fileName);
			map.put("contentType", contentType);
			map.put("size", size);
			
			json.add(map);
			
			String filePath=System.getProperty("user.dir")+"\\dev\\data\\pics\\"+attachment.getId()+"_"+fileName;
			File dest=new File(filePath);
			multipartFile.transferTo(dest);
		}	
		return json;
	}
}
