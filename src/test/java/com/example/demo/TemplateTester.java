package com.example.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class TemplateTester {
	private final WebDriver webDriver;

	private WebElement expression;

	public TemplateTester(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public String getExpresssion() {
		return this.expression.getText();
	}

	public TemplateTester toPublicTemplate(String templateName) {
		return to("/public/" + templateName);
	}

	public TemplateTester toAdminTemplate(String templateName) {
		return to("/admin/" + templateName);
	}

	public TemplateTester toUserTemplate(String templateName) {
		return to("/user/" + templateName);
	}

	private TemplateTester to(String url) {
		this.webDriver.get(url);
		System.out.println(this.webDriver.getPageSource());
		return PageFactory.initElements(this.webDriver, TemplateTester.class);
	}
}
