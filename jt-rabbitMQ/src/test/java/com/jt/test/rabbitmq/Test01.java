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

public class Test01 {
	private Connection connection = null;
	private String queue_name = "simple";	//简单模式
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
		/**
		 * 参数说明：
		 * 1.queue：队列的名称
		 * 2.durable：持久化	false表示不持久化 true表示持久化 当rabbitMQ服务器重启时
		 * 会恢复队列的内容。如果没有特殊的需要一般为false
		 * 3.exclusive：true表示生产者所独有，队列不能由消费者使用 一般为false
		 * 4.autoDelete：是否自动删除 当队列中没有消息时，该队列是否删除
		 * 				一般为false
		 * 5.arguments：额外的参数 一般为null
		 */
		channel.queueDeclare(queue_name, false, false, false, null);
		//定义队列消息
		String msg = "我是一个简单模式";
		//将消息发送到队列中
		/**
		 * 参数说明：
		 * 1.exchange：交换机的名称 如果需要交换机则添加名称 如果没有交换机，则""串
		 * 2.routingKey：路由Key 寻址的关键字，如果需要使用路由key定义
		 * 特定的关键字(orderKey.xxxx...) 如果不需要路由key，在简单模式中添加队列名称
		 * 3.props：其他的配置 一般为null
		 * 4.body：表示需要发送的消息(字节码文件)
		 */
		channel.basicPublish("", queue_name, null, msg.getBytes());
		//将流关闭
		channel.close();
		connection.close();
		System.out.println("消息队列发送成功！");
	}
	
	//定义消费者
	/**
	 * 1.创建通道
	 * 2.创建队列
	 * 3.构建消费者对象
	 * 4.将消费者与队列进行绑定
	 * 5.消费者从队列中获取消息
	 * 6.关闭流文件（while死循环，关闭流执行不到）
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ConsumerCancelledException 
	 * @throws ShutdownSignalException 
	 */
	//@Test
	public void consumer() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		//1.获取通道
		Channel channel = connection.createChannel();
		//2.定义消息队列
		channel.queueDeclare(queue_name, false, false, false, null);
		//3.创建消费者对象
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//4.将消费者与队列进行绑定
		/**
		 * queue：队列的名称
		 * autoAck：是否自动的回复 true自动回复 false手动的回复
		 * callback：一般添加消费者对象
		 */
		channel.basicConsume(queue_name, true, consumer);
		int a = 0;
		//5.消费者从队列中获取消息
		while(true){
			//通过迭代的方式遍历队列
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = "消费者获取队列中的数据,"+a+":"+new String(delivery.getBody());
			a++;
			System.out.println(msg);
		}
	}
}










