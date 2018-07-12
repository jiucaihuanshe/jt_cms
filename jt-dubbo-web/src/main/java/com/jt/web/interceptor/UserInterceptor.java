package com.jt.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.util.CookieUtils;
import com.jt.web.pojo.User;
import com.jt.web.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

//用来获取用户信息	HandlerInterceptor是SpringMVC中拦截器的接口
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/***
	 * preHandle Controller方法执行之前
	 * postHandle    COntroller方法执行之后
	 * afterCompletion 最终执行的方法
	 * 由于业务逻辑,拦截器应该在用户用户点击购车按钮时生效.
	 * 这样请求还没有转向. 应该使用preHandle
	 * 
	 * 如何获取用户信息:???
	 * 	1.先通过request获取Cookie
	 *  2.获取ticket信息
	 *  3.查询缓存操作
	 *  4.判断数据有效性,如果含有用户信息,则直接转向目标页面
	 *  如果用户信息为null.则重定向到登陆页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1.获取Cookie
		String ticket = CookieUtils.getCookieValue(request, "JT_TICKET");
		//2.判断cookie中是否有值
		if(!StringUtils.isEmpty(ticket)){
			//3.如果ticket不为null,通过缓存查询用户信息
			String userJSON = jedisCluster.get(ticket);
			/**
			 * 4.判断缓存数据是否为null
			 * 原因: 浏览器一直保存着cookie,redis中有缓存策略,
			 * 可能会删除过期的key.所以需要判断
			 */
			if(!StringUtils.isEmpty(userJSON)){
				//5.表示含有数据 不为null
				User user = 
						objectMapper.readValue(userJSON, User.class);
				//6.USER对象如何存储,才能在Cart中获取user信息
				//通过ThreadLocal实现数据的传递
				UserThreadLocal.setUser(user);
				//放行转向
				return true;
			}
		}	
		//用户没有登陆 进行页面转向SSO的登陆页面
		response.sendRedirect("/user/login.html");
		return false; //false表示拦截,不会放行目标页面
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
