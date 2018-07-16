package com.jt.search.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {

	//需要通过Solr实现数据的查询,需要引入配置文件
	@Autowired
	private HttpSolrServer httpSolrServer;
	@Override
	public List<Item> findItemByKey(String keyword) {
		//定义查询
		SolrQuery query = new SolrQuery();
		//设定查询的关键字
		query.setQuery(keyword);
		//设定分页的参数
		query.setStart(0);
		query.setRows(20);
		try {
			//solr根据查询条件实现查询
			QueryResponse queryResponse = httpSolrServer.query(query);
			//获取结果集对象
			List<Item> itemList = queryResponse.getBeans(Item.class);
			return itemList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
