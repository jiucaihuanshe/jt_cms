package com.jt.test.http;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	
	@Test
	public void testGet() throws ClientProtocolException, IOException{
		//1.建立HttpClient对象 用来发出http请求的
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//2.创建uri  访问网站的地址
		String uri = "https://item.jd.com/5618804.html";
		//3.定义请求的方式
		HttpGet httpGet = new HttpGet(uri);
		//4.通过httpClient发出get请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		//5.判断请求是否有效  获取响应页面的状态码
		if(response.getStatusLine().getStatusCode() == 200){
			//6.将实体Entity转化为String
			String result = EntityUtils.toString(response.getEntity());
			//7.将结果输出
			System.out.println(result);
		}	
	}
	
	@Test
	public void testPost(){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		String uri = "https://item.jd.com/5618804.html";
		
		HttpPost httpPost = new HttpPost(uri);
		
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);
			
			StatusLine statusLine = response.getStatusLine();
			System.out.println("协议的名称"+statusLine.getProtocolVersion());
			System.out.println("响应的状态码"+statusLine.getStatusCode());
			System.out.println("响应状态"+statusLine.getReasonPhrase());
			
			
			if(response.getStatusLine().getStatusCode() == 200){
				
				String result = EntityUtils.toString(response.getEntity());
				System.out.println(result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	

}
