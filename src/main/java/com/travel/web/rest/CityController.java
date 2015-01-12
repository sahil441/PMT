package com.travel.web.rest;

import java.util.Collection;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.model.City;
import com.travel.model.Tag;
import com.travel.repositories.CityRepository;
import com.travel.repositories.TagRepository;

@RestController
@RequestMapping("city")
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private TagRepository tagRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<City> getCities() {
		List<City> cities = cityRepository.findAll();
		return cities;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public City getCity(
			final @RequestParam(value = "id", required = false) Integer id) {
		if (id != null) {
			return cityRepository.findOne(id);
		}
		// Used for CREATE
		return new City();
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.DELETE)
	public void removeCity(final @PathVariable Integer id) {
		cityRepository.delete(id);
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public ResponseEntity<City> save(final @RequestBody @Valid City city,
			final BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new IllegalArgumentException("Invalid arguments.");

		ResponseEntity<City> rv;

		City toBeSaved = processCity(city);

		try {
			this.cityRepository.save(toBeSaved);
			rv = new ResponseEntity<>(toBeSaved, HttpStatus.OK);
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

	// TODO : How can it be automated using spring type converters?
	private City processCity(City city) {
		City returnValue = new City();
		returnValue.setName(city.getName());
		if (city.getId() != null) {
			returnValue = cityRepository.findByName(city.getName());
		}
		returnValue.setAbouttext(city.getAbouttext());
		returnValue.setCountry(city.getCountry());
		returnValue.setHowtoreach(city.getHowtoreach());
		Collection<Tag> tags = returnValue.getTags();
		tags.clear();
		for (Tag tag : city.getTags()) {
			tags.add(tagRepository.findByDisplayText(tag.getDisplayText()));
		}
		return returnValue;
	}
}
