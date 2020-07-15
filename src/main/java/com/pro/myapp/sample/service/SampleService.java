package com.pro.myapp.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pro.myapp.board.common.common.CommandMap;

public interface SampleService {

	//List<Map<String, Object>> selectBoardList(Map<String, Object> map)throws Exception;
	
	Map<String, Object> selectBoardList(Map<String,Object>map)throws Exception;

	void writeBoard(Map<String, Object> map, HttpServletRequest request)throws Exception;

	Map<String, Object> selectBoard(Map<String, Object> map)throws Exception;

	void modifyBoard(Map<String, Object> map,HttpServletRequest request)throws Exception;

	void deleteBoard(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> selectBoardDeleteList(Map<String, Object> map) throws Exception;

	void restoreBoard(Map<String, Object> map)throws Exception;

}
