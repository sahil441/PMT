package com.travel.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule.Priority;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	/**
	 * Maps all AngularJS routes to index so that they work with direct linking.
	 */

	@Controller
	static class Routes {
		// @formatter:off
		@RequestMapping({
		    "/city-list",
		    "/attraction-list",
		    "/welcome"
		})
		// @formatter:on
		public String index() {
			return "/index.html";
		}
	}

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return (ServletContext servletContext) -> {
			final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
			characterEncodingFilter.setEncoding("UTF-8");
			characterEncodingFilter.setForceEncoding(false);

			// TODO Blog
			servletContext.addFilter("characterEncodingFilter",
					characterEncodingFilter).addMappingForUrlPatterns(
					EnumSet.of(DispatcherType.REQUEST), false, "/*");
		};
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		// Max file Size is set to 5 M.B and max request size is set to 20 M.B
		return new MultipartConfigElement("", 5 * 1024 * 1024, 20 * 1024 * 1024,
				1024 * 1024);
	}

	/**
	 * Enable favor of format parameter over requested content type, needed for
	 * {@link OEmbedController#getEmbeddableTrack(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, javax.servlet.http.HttpServletRequest)}
	 *
	 * @param configurer
	 */
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		super.configureContentNegotiation(configurer);
		configurer.favorParameter(true);
	}

	/**
	 * This makes mapping of
	 * {@link TracksController#getTrack(java.lang.String, java.lang.String)} and
	 * the default mapping in separate methods possible.
	 *
	 * @return
	 */
	@Bean
	public BeanPostProcessor beanPostProcessor() {
		return new BeanPostProcessor() {

			@Override
			public Object postProcessBeforeInitialization(Object bean,
					String beanName) throws BeansException {
				if (bean instanceof RequestMappingHandlerMapping
						&& "requestMappingHandlerMapping".equals(beanName)) {
					((RequestMappingHandlerMapping) bean)
							.setUseRegisteredSuffixPatternMatch(true);
				}
				if (bean instanceof ThymeleafViewResolver
						&& "thymeleafViewResolver".equals(beanName)) {
					((ThymeleafViewResolver) bean)
							.setExcludedViewNames(new String[] { "/index.html" });
				}
				return bean;
			}

			@Override
			public Object postProcessAfterInitialization(Object bean,
					String beanName) throws BeansException {
				return bean;
			}
		};
	}

	/**
	 * {@link OEmbedResponse} uses XmlElement annotations to be configured for
	 * JAXB as well as JSON so we need the {@link JaxbAnnotationModule} as well
	 *
	 * @return
	 */
	@Bean
	public ObjectMapper jacksonObjectMapper() {
		return new ObjectMapper().registerModules(new JaxbAnnotationModule()
				.setPriority(Priority.SECONDARY));
	}
}
