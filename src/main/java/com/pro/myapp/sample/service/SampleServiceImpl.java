package com.pro.myapp.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pro.myapp.board.common.util.FileUtils;
import com.pro.myapp.sample.dao.SampleDAO;

@Service("sampleService") // Controller에서 사용할기 위한 선언
public class SampleServiceImpl implements SampleService {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "sampleDAO") // 빈을 주입
	private SampleDAO sampleDAO;

	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	@Override
	public Map<String, Object> selectBoard(Map<String, Object> map) throws Exception {
		sampleDAO.updateHitCnt(map);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("map", sampleDAO.selectBoard(map));
		resultMap.put("list", sampleDAO.selectFileList(map));
		return resultMap;
	}

	/*@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception { 
		sampleDAO.selectBoardList(map);
	}*/

	@Override
	public Map<String, Object> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
	}

	@Override
	public List<Map<String, Object>> selectBoardDeleteList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardDeleteList(map);
	}

	@Override
	public void restoreBoard(Map<String, Object> map) throws Exception {
		sampleDAO.restoreBoard(map);
	}

	@Override
	public void modifyBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		sampleDAO.modifyBoard(map);

		sampleDAO.deleteFile(map, request);

		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String, Object> tempMap = null;
		for (int i = 0; i < list.size(); i++) {
			tempMap = list.get(i);

			if (tempMap.get("IS_NEW").equals("Y")) {
				sampleDAO.insertFile(tempMap);
			} else {
				sampleDAO.updateFile(tempMap);
			}
		}
	}

	@Override
	public void writeBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		sampleDAO.writeBoard(map);

		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(map, request);

		for (int i = 0; i < list.size(); i++) {
			sampleDAO.insertFile(list.get(i));
		}
		/*
		 * MultipartHttpServletRequest multipartHttpServletRequest =
		 * (MultipartHttpServletRequest) request; Iterator<String> iterator =
		 * multipartHttpServletRequest.getFileNames(); MultipartFile multipartFile =
		 * null;
		 * 
		 * while(iterator.hasNext()) { multipartFile =
		 * multipartHttpServletRequest.getFile(iterator.next());
		 * if(multipartFile.isEmpty() == false) {
		 * log.debug("----------file start----------");
		 * log.debug("name : "+multipartFile.getName());
		 * log.debug("filename : "+multipartFile.getOriginalFilename());
		 * log.debug("size : "+multipartFile.getSize());
		 * log.debug("----------file end------------"); } }
		 */
	}

	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		sampleDAO.deleteBoard(map);
	}

}
