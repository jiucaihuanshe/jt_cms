package test;

import com.jt.dubbox.demo.ConsumerThd;

public class AppTest {
	public static void main(String[] args) {
		ConsumerThd thd = new ConsumerThd();
		thd.sayHello();
	}
}