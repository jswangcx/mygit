package com.ufgov.wcx.service;

import java.util.Map;

public interface HouseRegisterService {
	public void save(Map<String, Object> requestMap);
	public Map<Object,Object> select(Map<String, Object> requestMap);
}
