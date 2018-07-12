package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	
	/**
	 * 补充知识: 了解即可
	 * 		{id:1,name:tom,data:XXXXX}
	 * 		可以通过jsonNode对象获取JSON数据中的key和value值
			JsonNode jsonNode = objectMapper.readTree(resutJSON);
			String data = jsonNode.get("data").asText();
	 */
	@Override
	public String saveUser(User user) {
		//请求SSO的路径
		String uri = "http://sso.jt.com/user/register";
		//数据封装
		Map<String, String> map = new HashMap<String,String>();
		map.put("username", user.getUsername());
		map.put("password", user.getPassword());
		map.put("phone", user.getPhone());
		map.put("email", user.getEmail());
		try {
			//经过httpClient调用返回SysResult对象.首先应该查看
			//返回的状态码如果为200表示SSO一切正常
			String resultJSON = httpClient.doPost(uri, map);
			SysResult sysResult = 
			objectMapper.readValue(resultJSON, SysResult.class);
			if(sysResult.getStatus() == 200){
				return (String) sysResult.getData();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	//返回ticket数据
	@Override
	public String findUserByUP(String username, String password) {
		String uri = "http://sso.jt.com/user/login";
		Map<String, String> map = new HashMap<String,String>();
		//注意不要有空格  为了网络中传递的速度更快 采用简单字符传递
		map.put("u", username);
		map.put("p", password);
		try {	
			String resutJSON = httpClient.doPost(uri,map);
			//判断数据是否有效  将其转化为Sysresult对象
			//为了防止data数据为null时,返回的字符串为"null"对代码产生影响
			SysResult sysResult = 
			objectMapper.readValue(resutJSON, SysResult.class);			//判断SSO返回是否正确
			if(sysResult.getStatus() == 200){
				return (String) sysResult.getData();	
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
