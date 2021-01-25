package com.cms.main.listener;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cms.main.entity.LogEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Listener {

	@Autowired
	Map<Object, Object> HazelCastMessagesMap;

	@Autowired
	ObjectMapper mapper;

	@RabbitListener(queues = "LogStatusQueue")
	public void doListen(Message message) {
		String actMessage = new String(message.getBody());
		System.out.println(actMessage);
		try {
			LogEntity enitity = mapper.readValue(actMessage, LogEntity.class);
			HazelCastMessagesMap.put(enitity.getNarId(), enitity.getExceptionMessage());
			System.out.println(enitity.getNarId() + " : " + enitity.getExceptionMessage() + " :  "
					+ HazelCastMessagesMap.size());
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
