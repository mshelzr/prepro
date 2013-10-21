package com.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("VIEW")
public class NewPortlet {

	public NewPortlet() {
		System.out.println("Init NewPortlet");
	}

	@RequestMapping
	protected String defaultView(Model model) {
		String toModel="Hola, Elvis";
		model.addAttribute("toModel",toModel);
		return "view";
	}

}
