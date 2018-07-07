package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	//httpClient

	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public String saveUser(User user) {
		//请求sso的路径
		String uri = "http://sso.jt.com/user/register";
		//数据封装
		Map<String, String> map = new HashMap<>();
		map.put("username", user.getUsername());
		map.put("password", user.getPassword());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());
		try {
			//经过httpClient调用返回SysResult对象。首先应该查看返回的
			//状态码如果为200表示sso一切正常
			String resultJSON = httpClientService.doPost(uri,map);
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			//判断SSO返回是否正确
			if(sysResult.getStatus()==200){
				return (String) sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String findUserByUP(String username, String password) {
		//httpClient
		String uri= "http://sso.jt.com/user/login";
		Map<String, String> map = new HashMap<>();
		//注意不要有空格	为了网络中传递的速度更快 采用简单字符传递
		map.put("u", username);
		map.put("p", password);
		try {
			//{status:200,msg="addsf",data="xxxx"}
			String resultJSON = httpClientService.doPost(uri,map);
			//判断数据是否有效 将其转化为SysResult对象
			/**
			 * 判断数据是否有效 将其转化为SysResult对象
			 * 为了防止data数据为null时，返回的字符串为"null"
			 * 对代码产生影响
			 */
			SysResult sysResult = objectMapper.readValue(resultJSON, SysResult.class);
			
			/**
			 * 查看返回值的信息	解析JSON数据
			 * JsonNode jsonNode = objectMapper.readTree(resultJSON);
			 * String msg（运行的返回值: 200） = jsonNode.get("status").asText();
			 */
			
			if(sysResult.getStatus() == 200){
				return (String) sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
