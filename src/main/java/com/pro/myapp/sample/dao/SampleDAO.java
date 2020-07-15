package com.pro.myapp.sample.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.pro.myapp.board.common.dao.AbstractDAO;

@Repository("sampleDAO")	//DAO임을 선언하고 serive에서 사용할수 있도록 이름을 선언
public class SampleDAO  extends AbstractDAO{
	
	/*@SuppressWarnings("unchecked")	//unchecked = 검증되지 않은 연산자 관련 경고 억제
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map){
		return (List<Map<String, Object>>)selectList("sample.selectBoardList",map);	//인자 두가지, 펏번째는 쿼리 이름, 두번째는 쿼리가 실행되는데 필요한 변수들
	}*/															//mapper의 sample에 selectBoardList 호출
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardList(Map<String, Object>map)throws Exception{
		return (Map<String, Object>)selectPagingList("sample.selectBoardList",map);
	}
	
	public void writeBoard(Map<String, Object> map) {
		insert("sample.writeBoard", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoard(Map<String, Object> map) {
		return (Map<String, Object>)selectOne("sample.selectBoard", map);
	}

	public void updateHitCnt(Map<String, Object> map) {
		update("sample.updateHitCnt", map);
	}

	public void modifyBoard(Map<String, Object> map) {
		update("sample.modifyBoard", map);
	}

	public void deleteBoard(Map<String, Object> map) {
		update("sample.deleteBoard", map);
		System.out.println(map);
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardDeleteList(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("sample.selectBoardDeleteList",map);	
	}

	public void restoreBoard(Map<String, Object> map) {
		update("sample.restoreBoard", map);
	}

	public void insertFile(Map<String, Object> map) {
		insert("sample.insertFile", map);
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFileList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>)selectList("sample.selectFileList",map);
	}

	public void deleteFile(Map<String, Object> map, HttpServletRequest request) {
		update("sample.deleteFile",map);
	}

	public void updateFile(Map<String, Object> map) {
		update("sample.updateFile", map);
	}
}
