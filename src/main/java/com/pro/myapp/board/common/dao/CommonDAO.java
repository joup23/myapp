package com.pro.myapp.board.common.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("commonDAO")
public class CommonDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception{
		// TODO Auto-generated method stub
		return (Map<String, Object>)selectOne("common.selectFileInfo",map);
	}

}
