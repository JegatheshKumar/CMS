package com.csm.client.main;

import java.io.IOException;
import java.util.Calendar;

import com.csm.client.main.entity.LogEntity;
import com.csm.client.main.utilities.HttpUtils;

public class CSMLoggerClient {

	public static synchronized void sendParam(LogEntity entity) throws Exception {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
			HttpUtils.sendToCMS(entity);
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}

	public static void main(String[] args) throws Exception {

		String message = "message_"+"tesyrdt";
		for (int i = 10; i <= 15; i++) {
			message+=i+i;
			System.out.println("Sending message : " + message);
			sendParam(new LogEntity("message_" + i, new Exception(message + i), Calendar.getInstance().getTime(), ""));

		}

	}

}
