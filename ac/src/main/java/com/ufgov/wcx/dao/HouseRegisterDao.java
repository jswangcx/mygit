package com.ufgov.wcx.dao;

import java.util.List;
import java.util.Map;

import com.ufgov.ip.MyBatisDao;

@MyBatisDao
public interface HouseRegisterDao{
	public void save(Map<String, Object> requestMap);
	public List<Map<String,String>> select();
}
