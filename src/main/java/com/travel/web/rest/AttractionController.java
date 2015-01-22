package com.travel.web.rest;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travel.model.Attachment;
import com.travel.model.Attraction;
import com.travel.repositories.AttractionRepository;

@RestController
@RequestMapping("attraction")
public class AttractionController {

	@Autowired
	private AttractionRepository attractionRepository;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public List<Attraction> getAttractions() {
		return attractionRepository.findAll();
	}

	@RequestMapping(value = "/form/", method = RequestMethod.GET)
	public Attraction newAttraction(final @PathVariable Integer id) {
		return new Attraction();
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public Attraction getAttraction(final @PathVariable Integer id) {
		return attractionRepository.findOne(id);
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.POST)
	public Attraction updateAttraction(final @PathVariable Integer id,
			final @RequestBody @Valid Attraction attraction) {
		/*	copyAttractionData(attraction,toAttraction);*/
		return attractionRepository.save(attraction);
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.DELETE)
	public void removeAttraction(final @PathVariable Integer id) {
		Attraction attraction = attractionRepository.findOne(id);
		String filePath;
		for(Attachment attachment: attraction.getAttachments()) {
			filePath=System.getProperty("user.dir")+"\\dev\\data\\pics\\"+attachment.getId()+"_"+attachment.getFileName();
			File file=new File(filePath);
			if(file.exists()) {
				file.delete();
			}
		}	
		attractionRepository.delete(id);
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public ResponseEntity<Attraction> save(final @RequestBody @Valid Attraction attraction,
			final BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new IllegalArgumentException("Invalid arguments.");

		ResponseEntity<Attraction> rv;

		try {
			this.attractionRepository.save(attraction);
			rv = new ResponseEntity<>(attraction, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			rv = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return rv;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(
			IllegalArgumentException e) throws Exception {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}


}
