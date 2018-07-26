package com.tedu.jsoup;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tedu.jsoup.pojo.Book;

public class TestJSOUP {
	private static ObjectMapper objectMapper=new ObjectMapper();
	/**
	 * 1.爬取静态页面数据 获取页面标题
	 * 2.定位目标网站的页面
	 * 3.定位页面的元素内容
	 * 4.获取具体的数据
	 * 5.数据处理
	 * 6.入库保存
	 */
	@Test
	public void test01(){
		String url = "http://club.xywy.com/list_346_answer.htm";
		//通过JSOUP进行数据爬取
		try {
			Document document = Jsoup.connect(url).get();
			//通过jsoup提供的选择器快速定位目标元素
			for(int i=1;i<=40;i++){
				Element element = document.select(".kstable a").get(i);
				String aLink = element.select("a").first().attr("abs:href");
				//获取h3的具体文本内容
				String msg = element.text();
				System.out.println(msg);
				System.out.println(aLink);
				i++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test02(){
		String url="http://www.tmooc.cn/";
		try {
			Element element = Jsoup.connect(url).get().select(".num-box-lty h3").get(2);
			String msg = element.text();
			System.out.println("用户数量："+msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03(){
		String url = "http://uc.tmooc.cn/userValidate/getUserInfo?_=1531796079483";
		try {
			Response response = Jsoup.connect(url).ignoreContentType(true).execute();
			//将获取到的结果转化为字符串
			String resultJSON = response.body();
			System.out.println(resultJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test04(){
		String url ="http://uc.tmooc.cn/course/getCollectionSingupInfo";
		try {
			Response response = Jsoup.connect(url).ignoreContentType(true).execute();
			//将获取到的结果转化为字符串
			String resultJSON = response.body();
			//System.out.println(resultJSON);
			//String listJSON =objectMapper.readTree(resultJSON).toString();
			//System.out.println(listJSON);
			
			objectMapper.readValue(resultJSON, Book.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
