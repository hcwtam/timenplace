package com.example.restservice;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventsController {

	public static String asString(Resource resource) throws IOException {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

	@ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleQueryParamException(IOException e) {
        return new ResponseEntity<>("Incorrect param(s)", HttpStatus.BAD_REQUEST);
    }

	@GetMapping("/events")
	public Events events(@RequestParam String country, @RequestParam int year) throws IOException {
        String filename = String.format("resources/%d_in_%s.html", year, country.replace(" ", "_"));

        ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:" + filename);
		
		String content = asString(resource);

		return new Events(content, year);
	}
}