package com.ewallet.config;

import org.springframework.context.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
			"EWallet API",
			"Java Case Study for e-wallet",
			"API TOS",
			"Terms of Service",
			new Contact("Vineet Gupta", "www.example.com", "vineetgupta_1991@yahoo.com"),
			"License of API", "API license URL", Collections.emptyList());
	}

	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("api-1.0")
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(regex("/.*"))
			.build()
			.apiInfo(apiInfo());

	}

}