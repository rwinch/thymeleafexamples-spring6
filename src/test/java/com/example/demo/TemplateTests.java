package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
class TemplateTests {
	@MockBean
	AclService aclService;

	TemplateTester templateTester;

	@BeforeEach
	void setup(@Autowired WebDriver webDriver) {
		this.templateTester = new TemplateTester(webDriver);
	}

	@Test
	@WithMockUser
	void principalNameWhenUser() {
		assertThat(this.templateTester.toUserTemplate("principalName").getExpresssion()).isEqualTo("user");
	}

	@Test
	void principalNameWhenAnonymous() {
		assertThat(this.templateTester.toPublicTemplate("principalName").getExpresssion()).isEqualTo("anonymousUser");
	}

	@Test
	@WithMockUser
	void authorizeUrlWhenUser() {
		assertThat(this.templateTester.toPublicTemplate("authorizeUrl").getExpresssion()).isNotEmpty();
	}

	@Test
	void authorizeUrlWhenAnonymous() {
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> this.templateTester.toPublicTemplate("authorizeUrl").getExpresssion());
	}

	@Test
	@WithMockUser
	void authorizeExpressionWhenUser() {
		assertThat(this.templateTester.toPublicTemplate("authorizeExpression").getExpresssion()).isNotEmpty();
	}

	@Test
	void authorizeExpressionWhenAnonymous() {
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> this.templateTester.toPublicTemplate("authorizeExpression").getExpresssion());
	}

	@Test
	@WithMockUser
	void authorizeAclWhenUser() {
		Acl acl = aclWillReturn(true);
		given(this.aclService.readAclById(any(), any())).willReturn(acl);
		assertThat(this.templateTester.toPublicTemplate("authorizeAcl").getExpresssion()).isNotEmpty();
	}

	@Test
	void authorizeAclWhenAnonymous() {
		Acl acl = aclWillReturn(false);
		given(this.aclService.readAclById(any(), any())).willReturn(acl);
		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> this.templateTester.toPublicTemplate("authorizeAcl").getExpresssion());
	}

	private static Acl aclWillReturn(boolean result) {
		Acl acl = mock(Acl.class);
		given(acl.isGranted(any(), any(), anyBoolean())).willReturn(result);
		return acl;
	}
}
