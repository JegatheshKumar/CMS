package com.csm.client.main.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.csm.client.main.entity.LogEntity;
import com.csm.client.main.entity.RabbitMQPropertiesBuilder;
import com.csm.client.main.entity.RabbitMQPropertiesBuilder.RabbitMQProperties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class HttpUtils {

	private static final String DO_NOT_PARTICIPATE = "@com.csm.client.main.annotations.DoNotParticipate()";
	private static final ConnectionFactory connectionFactory = new ConnectionFactory();

	public static void sendToCMS(LogEntity entity)
			throws IllegalArgumentException, IllegalAccessException, IOException {

		Field[] fields = entity.getClass().getFields();
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		for (Field field : fields) {
			boolean isParticipant = false;
			if (field.getAnnotations().length == 0) {
				jsonBuilder.add(field.getName(), field.get(entity).toString());
			} else {
				for (Annotation annotation : field.getAnnotations()) {
					isParticipant = canParticipate(annotation);
					if (isParticipant) {
						jsonBuilder.add(field.getName(), field.get(entity).toString());
					}
				}
			}

		}
		JsonObject logJson = jsonBuilder.build();
		System.out.println(logJson);
		String propertyPath = "src/com/csm/client/main/properties/client.properties";

		String[] classPaths = System.getProperty("java.class.path").split(":")[0].split("/");
		String initialPath = "/";

		for (int i = 1; i < classPaths.length - 1; i++) {
			initialPath += classPaths[i] + "/";
		}
		Properties QueueProperties = new Properties();

		QueueProperties.load(new FileInputStream(initialPath + propertyPath));

		sendToQueue(new RabbitMQPropertiesBuilder().queueName(QueueProperties.getProperty("queueName"))
				.user(QueueProperties.getProperty("user")).password(QueueProperties.getProperty("password"))
				.host(QueueProperties.getProperty("host")).port(Integer.parseInt(QueueProperties.getProperty("port")))
				.build(), logJson);

	}

	public static void main(String args[]) throws FileNotFoundException, IOException {
		Properties QueueProperties = new Properties();
		String path = "/media/jegan/PartitionTwo/CentralMonitoringSystem/cms- client-dist";
		System.out.println(path + "/src/" + HttpUtils.class.getPackage().toString().split(" ")[1].replace(".", "/"));
//		QueueProperties.load(new FileInputStream(""));
//		System.out.println(QueueProperties.get("user"));

	}

	private static void sendToQueue(RabbitMQProperties QueueProp, JsonObject logJson) {

		connectionFactory.setHost(QueueProp.getHost());
		connectionFactory.setUsername(QueueProp.getUserName());
		connectionFactory.setPassword(QueueProp.getPassWord());
		connectionFactory.setPort(QueueProp.getPortNumber());

		try (Connection conn = connectionFactory.newConnection(); Channel channel = conn.createChannel();) {
			String filLogJson = logJson.toString();
			channel.queueDeclare(QueueProp.getQueueName(), true, false, false, null);
			channel.basicPublish("", QueueProp.getQueueName(), null, filLogJson.getBytes());
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean canParticipate(Annotation annotation) {

		if (DO_NOT_PARTICIPATE.equals(annotation.toString())) {
			return false;
		}

		return true;
	}
}
