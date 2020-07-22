package com.cjrequena.sample.sourcesample.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
  info = @Info(title = "My App",
  description = "Some long and useful description",
  version = "v1"))
public class OpenApiConfiguration {

//  @Bean
//  public OpenAPI customOpenAPI() {
//    return new OpenAPI()
//      .components(new Components())
//      .info(new io.swagger.v3.oas.models.info.Info()
//        .title("spring-cloud-stream-sample")
//        .description("spring-cloud-stream-sample").version("v1"));
//  }

}
