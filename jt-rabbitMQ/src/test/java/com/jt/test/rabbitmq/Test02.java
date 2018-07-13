package com.jt.test.rabbitmq;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Test02 {
	private Connection connection = null;
	private String queue_name = "work";	//工作模式
	//连接rabbitmq
	//@Before
	public void init() throws IOException{
		//创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//为工厂对象添加数据 远程主机 端口号 虚拟主机名称 用户名和密码
		factory.setHost("192.168.56.134");
		factory.setPort(5672);
		factory.setVirtualHost("/jt");
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		//创建连接
		connection = factory.newConnection();
	}
	
	//定义消息的生产者
	//@Test
	public void provider() throws IOException{
		//1.创建通道
		Channel channel = connection.createChannel();
		//2.创建队列
		channel.queueDeclare(queue_name, false, false, false, null);
		//定义队列消息
		String msg = "我是一个工作模式~~~~~~双工模式";
		//将消息发送到队列中
		channel.basicPublish("", queue_name, null, msg.getBytes());
		//将流关闭
		channel.close();
		connection.close();
		System.out.println("消息队列发送成功！");
	}
	
	//消费者1
	//@Test
	public void consumer1() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue_name, false, false, false, null);
		//需要对消费个数进行定义
		//消费者允许有三次没有响应给rabbitMQ，如果长时间没有响应则不允许再次消费消息
		channel.basicQos(1);
		//定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//将消费者与队列进行绑定 false表示手动返回
		channel.basicConsume(queue_name, false, consumer);
		//获取消息
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = "消费者A"+new String(delivery.getBody());
			System.out.println(msg);
			//告知rabbitMQ我当前消费的是哪一个消息
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
	
	//消费者2
	//@Test
	public void consumer2() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue_name, false, false, false, null);
		//需要对消费个数进行定义
		//消费者允许有三次没有响应给rabbitMQ，如果长时间没有响应则不允许再次消费消息
		channel.basicQos(1);
		//定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//将消费者与队列进行绑定 false表示手动返回
		channel.basicConsume(queue_name, false, consumer);
		//获取消息
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = "消费者B"+new String(delivery.getBody());
			System.out.println(msg);
			//告知rabbitMQ我当前消费的是哪一个消息
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
}










