package com.remote.doctor;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.remote.doctor.converter.ClientDtoToClientConverter;
import com.remote.doctor.converter.ClientToClientDtoConverter;
import com.remote.doctor.converter.DiscussionDtoToDiscussionConverter;
import com.remote.doctor.converter.DiscussionToDiscussionInfoDto;
import com.remote.doctor.converter.DoctorDtoToDoctorConverter;
import com.remote.doctor.converter.DoctorToDoctorDtoConverter;
import com.remote.doctor.converter.MessageDtoToMessageConverter;
import com.remote.doctor.converter.MessageToMessageDtoConverter;
import com.remote.doctor.converter.UserToCopyWriterConverter;

@SpringBootApplication
public class RemoteDoctorApplication implements WebMvcConfigurer{
	@Autowired
	private BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(RemoteDoctorApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver(){
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return  localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor(){
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		UserToCopyWriterConverter copyWriterConverter = new UserToCopyWriterConverter(encoder);

		registry.addConverter(new DiscussionToDiscussionInfoDto(copyWriterConverter));
		registry.addConverter(new DiscussionDtoToDiscussionConverter());
		registry.addConverter(new ClientDtoToClientConverter(encoder));
		registry.addConverter(new DoctorDtoToDoctorConverter(encoder));
		registry.addConverter(new MessageDtoToMessageConverter());
		registry.addConverter(new MessageToMessageDtoConverter());
		registry.addConverter(new DoctorToDoctorDtoConverter());
		registry.addConverter(new ClientToClientDtoConverter());
		registry.addConverter(copyWriterConverter);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

}
