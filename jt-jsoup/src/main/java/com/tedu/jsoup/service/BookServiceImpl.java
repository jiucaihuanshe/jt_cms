package com.tedu.jsoup.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tedu.jsoup.mapper.BookMapper;

public class BookServiceImpl implements BookService {
	@Autowired
	private BookMapper bookMapper;
	@Override
	public void insertBook(String url) {

	}

}
