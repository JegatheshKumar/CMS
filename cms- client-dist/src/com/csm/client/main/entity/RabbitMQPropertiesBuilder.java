package com.csm.client.main.entity;

public class RabbitMQPropertiesBuilder {

	public RabbitMQPropertiesBuilder() {

	}

	private final RabbitMQProperties rabbitMqProps = new RabbitMQProperties();

	public RabbitMQProperties build() {
		return rabbitMqProps;
	}

	public RabbitMQPropertiesBuilder host(String host) {
		rabbitMqProps.host = host;
		return this;
	}

	public RabbitMQPropertiesBuilder user(String userName) {
		rabbitMqProps.userName = userName;
		return this;
	}

	public RabbitMQPropertiesBuilder password(String passWord) {
		rabbitMqProps.passWord = passWord;
		return this;
	}

	public RabbitMQPropertiesBuilder queueName(String queueName) {
		rabbitMqProps.queueName = queueName;
		return this;
	}

	public RabbitMQPropertiesBuilder port(int portNumber) {
		rabbitMqProps.portNumber = portNumber;
		return this;
	}

	public class RabbitMQProperties {

		RabbitMQProperties() {
		}

		String host;
		String userName;
		String passWord;
		String queueName;
		int portNumber;

		public String getHost() {
			return host;
		}

		public String getUserName() {
			return userName;
		}

		public String getPassWord() {
			return passWord;
		}

		public String getQueueName() {
			return queueName;
		}

		public int getPortNumber() {
			return portNumber;
		}

		@Override
		public String toString() {
			return "RabbitMQProperties [host=" + host + ", userName=" + userName + ", passWord=" + passWord
					+ ", queueName=" + queueName + ", portNumber=" + portNumber + "]";
		}

	}

}
