package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TemplateController {

	@GetMapping(value = {"/public/{templateName}", "/admin/{templateName}", "/user/{templateName}"})
	String index(@PathVariable String templateName) {
		return templateName;
	}

	@ModelAttribute("domainObject")
	DomainObject domainObject() {
		return new DomainObject();
	}
}
