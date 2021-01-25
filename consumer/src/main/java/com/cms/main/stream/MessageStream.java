package com.cms.main.stream;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class MessageStream {
	
	SseEmitter emitter;

	public MessageStream(SseEmitter emitter) {
		this.emitter = emitter;
	}
	

}
