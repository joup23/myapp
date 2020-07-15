package com.pro.myapp.board.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class AbstractDAO {
	protected Log log = LogFactory.getLog(AbstractDAO.class);

	@Autowired
	private SqlSessionTemplate sqlSession; // context-mapper.xml에서의 빈객체를 가져와서 @Autowired를 통해 자동으로 의존 주입
											// sqlSession 값을 이용해 쿼리를 사용

	protected void printQueryId(String queryId) {
		if (log.isDebugEnabled()) {
			log.debug("\t QueryId \t : " + queryId);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map selectPagingList(String queryId, Object params){
	    printQueryId(queryId);
	      
	    Map<String,Object> map = (Map<String,Object>)params;
	    PaginationInfo paginationInfo = null;
	      
	    if(map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
	        map.put("currentPageNo","1");
	      
	    paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
	    if(map.containsKey("PAGE_ROW") == false || StringUtils.isEmpty(map.get("PAGE_ROW")) == true){
	        paginationInfo.setRecordCountPerPage(15);
	    }
	    else{
	        paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("PAGE_ROW").toString()));
	    }
	    paginationInfo.setPageSize(10);
	 
	    int start = paginationInfo.getFirstRecordIndex();
	    int end = paginationInfo.getRecordCountPerPage();
	    map.put("START",start);
	    map.put("END",end);
	      
	    params = map;
	     
	    Map<String,Object> returnMap = new HashMap<String,Object>();
	    List<Map<String,Object>> list = sqlSession.selectList(queryId,params);
	      
	    if(list.size() == 0){
	        map = new HashMap<String,Object>();
	        map.put("TOTAL_COUNT",0); 
	        list.add(map);
	          
	        if(paginationInfo != null){
	            paginationInfo.setTotalRecordCount(0);
	            returnMap.put("paginationInfo", paginationInfo);
	        }
	    }
	    else{
	        if(paginationInfo != null){
	            paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("TOTAL_COUNT").toString()));
	 
	            returnMap.put("paginationInfo", paginationInfo);
	        }
	    }
	    returnMap.put("result", list);
	    return returnMap;
	}


	public Object insert(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}

	public Object update(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}

	public Object delete(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}

	public Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}

	public Object selectOne(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
}
