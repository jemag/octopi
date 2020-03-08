package ca.ogsl.octopi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OpenAPIConfiguration implements WebMvcConfigurer {
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    
    registry
        .addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    
    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
  
//  @Bean
//  public GroupedOpenApi groupOpenApi() {
//    return GroupedOpenApi.builder()
//        .setGroup("v1")
//        .pathsToMatch("/api/v1/**")
//        .build();
//  }
  
  @Bean
  public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Octopi")
            .description("An API for storing and accessing the configuration necessary for displaying map layers")
            .contact(new Contact().name("CIOOS Info").email("info@cioos.ca"))
            .version("1")
            .license(new License().name("License of API").url(""))
            .termsOfService(""));
  }
  
}
