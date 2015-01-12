package com.travel.web.rest;

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

import com.travel.model.Tag;
import com.travel.repositories.TagRepository;

@RestController
@RequestMapping("tag")
public class TagController {

	@Autowired
	private TagRepository tagRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Tag> getTags() {
		List<Tag> tags = tagRepository.findAll();
		return tags;
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public Tag getTag(final @PathVariable Integer id) {
		return tagRepository.getOne(id);
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.DELETE)
	public void removeCity(final @PathVariable Integer id) {
		tagRepository.delete(id);
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public ResponseEntity<Tag> save(final @RequestBody @Valid Tag tag,
			final BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new IllegalArgumentException("Invalid arguments.");

		ResponseEntity<Tag> rv;

		try {
			this.tagRepository.save(tag);
			rv = new ResponseEntity<>(tag, HttpStatus.OK);
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
