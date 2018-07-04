package com.jt.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	// 定义格式转化工具
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ItemCatMapper itemCatMapper;
	//通过Spring的方式实现依赖注入
	//@Autowired
	//private RedisService redisService;	//redis分片--redis哨兵
	
	//通过集群的方式实现缓存
	@Autowired
	private JedisCluster jedisCluster;
	
	//通过spring动态注入的方式引入key
	//属性不能添加static，否则spring不能注入
	@Value("${ITEM_CAT_KEY}")
	private String ITEM_CAT_KEY;
	
	//通过单节点实现缓存操作,但是不实用
	//@Autowired
	//private Jedis jedis;

	@Override
	public List<ItemCat> findItemCat() {
		// 调用通用Mapper的方法
		// 如果查询全部数据则不需要设定参数直接为null
		return itemCatMapper.select(null);
	}
	
	/**
	 * 将商品分类信息插入到缓存中 思路： redis中通过key-value 
	 * 1.当用户查询数据时首先应该从缓存中获取数据
	 * 2.如果数据不为null,则代表缓存中有该数据,数据类型为JSON 
	 * 	a.需要将JSON串转化为对象后返回给用户
	 * 3.如果数据为null,表示缓存中没有该数据,则需要连接数据库进行查询. 
	 * 	a.将查询到的数据,转化为JSON存入redis中,方便下次使用.
	 */
	@Override
	public List<ItemCat> findItemCatByParentId(Long parentId) {
		// 1.定义查询的key值
		String key = "ITEM_CAT_" + parentId;
		// 2.根据key值查询缓存数据
		String dataJSON = jedisCluster.get(key);
		// 最后定义公用的List集合
		List<ItemCat> itemCatList = new ArrayList<>();
		try {
			// 3.判断返回值是否含有数据
			if (StringUtils.isEmpty(dataJSON)) {
				// 证明缓存中没有数据,则通过数据查询数据
				ItemCat itemCat = new ItemCat();
				itemCat.setParentId(parentId); // 设置父级id
				itemCat.setStatus(1); // 设置为正常的数据1
				itemCatList = itemCatMapper.select(itemCat);
				// 将返回数据转化为JSON串 [{},{},{}]
				String jsonResult = objectMapper.writeValueAsString(itemCatList);
				// 将数据存入缓存中
				jedisCluster.set(key, jsonResult);
			} else {
				// 表示数据不为空 需要将JSON串转化为List<ItemCat>集合对象
				// [{},{},{}] {id:1,name:"tom"}
				ItemCat[] itemCats = objectMapper.readValue(dataJSON, ItemCat[].class);
				for (ItemCat itemCat : itemCats) {
					// 向集合中赋值
					itemCatList.add(itemCat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatList;
	}

	/**
	 * 1.首先查询全部商品分类信息
	 * 2.通过Map将数据进行分类保存
	 * 3.准备返回值数据ItemCatResult对象
	 * 4.准备一级菜单列表集合 data
	 * 5.dataList赋值添加一级菜单
	 * 6.准备二级菜单 List
	 * 7.将二级菜单追加到一级菜单中
	 * 8.准备三级菜单List
	 * 9.将三级菜单追加到二级菜单中
	 */
	@Override
	public ItemCatResult findItemCatAll() {
		//查询全部可用的商品分类信息
		ItemCat itemCatTemp = new ItemCat();
		itemCatTemp.setStatus(1);	//表示可用
		List<ItemCat> itemCatList= itemCatMapper.select(itemCatTemp);
		
		//准备Map数据结构将商品信息进行分类 Long代表parentId
		Map<Long, List<ItemCat>> map = new HashMap<>();
		for(ItemCat itemCat : itemCatList){
			if(map.containsKey(itemCat.getParentId())){
				//如果存入parentId证明之前有数据，只需要做数据的追加即可。
				map.get(itemCat.getParentId()).add(itemCat);
			}else{
				//表示第一次插入数据
				List<ItemCat> tempList = new ArrayList<>();
				tempList.add(itemCat);
				map.put(itemCat.getParentId(), tempList);
			}
		}
		//通过上述的转化已经将数据完成了清洗
		//定义一级菜单信息
		List<ItemCatData> itemCatDataList1= new ArrayList<>();
		//开始查询全部的一级菜单
		for (ItemCat itemCat1 : map.get(0L)) {
			//定义一级菜单对象
			ItemCatData itemCatData1 = new ItemCatData();
			itemCatData1.setUrl("/products/"+itemCat1.getId()+".html");
			itemCatData1.setName("<a href='"+itemCatData1.getUrl()+"'>"+itemCat1.getName()+"</a>");
			
			//定义二级菜单对象
			List<ItemCatData> itemCatDataList2 = new ArrayList<>();
			for (ItemCat itemCat2 : map.get(itemCat1.getId())) {
				ItemCatData itemCatData2 = new ItemCatData();
				itemCatData2.setUrl("products/"+itemCat2.getId());
				itemCatData2.setName(itemCat2.getName());
				
				//定义三级菜单对象
				List<String> itemCatDataList3 = new ArrayList<>();
				for(ItemCat itemCat3 : map.get(itemCat2.getId())){
					itemCatDataList3.add("/products/"+itemCat3.getId()+"|"+itemCat3.getName());
				}
				itemCatData2.setItems(itemCatDataList3);
				itemCatDataList2.add(itemCatData2);
			}
			//新增二级菜单
			itemCatData1.setItems(itemCatDataList2);
			//将一级菜单信息保存到一级菜单列表中
			itemCatDataList1.add(itemCatData1);
			//控制菜单的个数,数量达标后跳出循环
			if(itemCatDataList1.size()>13){
				break;
			}
		}
		ItemCatResult catResult = new ItemCatResult();
		//保存一级商品菜单信息
		catResult.setItemCats(itemCatDataList1);
		return catResult;
	}
	
	/**
	 * 编程思想：
	 * 1.当原有的业务逻辑已经很复杂时不建议进行二次开发
	 * 2.当实现自己业务逻辑方法时不要修改别人的方法
	 * 3.如果业务逻辑相对复杂，尽可能的将方法拆分
	 * 需求：引入缓存技术
	 * 说明：如果key的关键字是自己单独使用，可以直接写死。如果可以的值
	 * 需要多人一起使用，则最好使用依赖注入的形式进行赋值。
	 * 缓存的实现：
	 * 1.当用户查询数据时，首先应该去缓存中获取数据。
	 * 2.如果缓存中没有该数据，则查询数据库获取
	 * 3.将数据转化为json存入缓存中
	 * 4.如果缓存中含有该数据，则直接讲json转化为对象返回。
	 * @return
	 */
	public ItemCatResult findItemCatAllByCache(){
		ItemCatResult itemCatResult = null;
		//从缓存中获取数据
		String jsonData = jedisCluster.get(ITEM_CAT_KEY);
		try {
			if(StringUtils.isEmpty(jsonData)){
				itemCatResult = findItemCatAll();
				//将对象转换为json数据
				String jsonItemCat = objectMapper.writeValueAsString(itemCatResult);
				//将数据存入redis中
				jedisCluster.set(ITEM_CAT_KEY, jsonItemCat);
			}else{
				//表示JSON中有数据则直接转换为对象
				itemCatResult = objectMapper.readValue(jsonData, ItemCatResult.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatResult;
	}
}
		/*@Override
		public List<ItemCat> findItemCatByParentId(Long parentId) {
			// 证明缓存中没有数据,则通过数据查询数据
			ItemCat itemCat = new ItemCat();
			itemCat.setParentId(parentId); // 设置父级id
			itemCat.setStatus(1); // 设置为正常的数据1
			List<ItemCat> itemCatList = itemCatMapper.select(itemCat);
			return itemCatList;
		 */
		/**
		 * 通用Mapper中带参数的查询 如果需要带参数查询，只需要将参数set到具体的对象中即可。 实现思路：
		 * 通用Mapper会将对象中不为null的数据当做where条件进行查询 lg:select id,parent_id... from
		 * tb_item_cat where parent_id = 0 and status=1
		 */

		// 测试缓存是否成功
		// System.out.println(jedis.get("tomcat"));
	

