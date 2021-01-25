package com.cms.main.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class Controller {

	SseEmitter globalEmitter = new SseEmitter();
	
	@GetMapping("/async-retrieve")
	public ResponseEntity<SseEmitter> doNotify() throws InterruptedException, IOException {

		SseEmitter emitter = new SseEmitter();
		
		
		
		return new ResponseEntity<>(emitter, HttpStatus.OK);

	}

}
