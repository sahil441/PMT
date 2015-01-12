package com.travel.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/welcome")
public class WelcomeController {

	@RequestMapping(value = "/msg", method = RequestMethod.GET)
	public String welcome() {
		return "Welcome to PenUT. Check this space for more!";
	}

}
