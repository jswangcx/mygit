package com.ufgov.wcx.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ufgov.wcx.dao.HouseRegisterDao;
import com.ufgov.wcx.service.HouseRegisterService;

@Service
public class HouseRegisterServiceImpl implements HouseRegisterService {

	@Autowired
	private HouseRegisterDao houseRegisterDao;
	
	@Override
	public void save(Map<String, Object> requestMap) {
		houseRegisterDao.save(requestMap);
	}

	@Override
	public Map<Object,Object> select(Map<String, Object> requestMap) {
		Map<Object,Object> map = new HashMap<Object,Object>();
		//使用分页插件,核心代码就这一行---页数,每页行数
		Page<Integer> pages = PageHelper.startPage((int)requestMap.get("currentPage"), (int)requestMap.get("pageSize"));
		//在mapper.xml中不要加limit分页，插件会自动拦截和添加limit分页
		map.put("records", houseRegisterDao.select());
		//pages.getTotal()获得总页数
		map.put("total", pages.getTotal());
	    return map;
	}

}
