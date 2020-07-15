package com.pro.myapp.board.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtils")
public class FileUtils {

	String filePath = "C:\\spring\\file\\";
	Logger log = Logger.getLogger(this.getClass());

	public List<Map<String, Object>> parseInsertFileInfo(Map<String, Object> map, HttpServletRequest request)
			throws Exception {
		MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;

		String original_Name = null;
		String original_Extension = null;
		String stored_Name = null;

		MultipartFile mulFile = null;
		Iterator<String> iterator = mulRequest.getFileNames();

		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>(); // 클라이언트에서 전송된 파일 정보를담아서 반환을 해줄 List
		Map<String, Object> fileMap = null;

		String board_IDX = (String) map.get("IDX").toString(); // ServiceImpl 에서 전달해준 map에서 신규 생성되는 게시글 번호를 받아오도록

		File file = new File(filePath);
		if (file.exists() == false) {
			file.mkdirs();
		}
		while (iterator.hasNext()) { // 파일의 정보를 받아서 새로운 이름으로 변경하는 부분
			mulFile = mulRequest.getFile(iterator.next());

			if (mulFile.isEmpty() == false) { // 파일이 없을때 까지
				original_Name = mulFile.getOriginalFilename(); // 파일의 원본이름을 저장
				original_Extension = mulFile.getOriginalFilename().substring(original_Name.lastIndexOf("."));// 해당 파일
																												// 이름의
																												// 확장자를
																												// 알아냄
				stored_Name = CommonUtils.getRandomString() + original_Extension; // getRandomString메서드를 이용해 32자리의 랜덤한
																					// 파일이름을 생성하고 원본 확장자를 붙임

				file = new File(filePath + stored_Name); // 경로에 파일을 저장하는 부분
				mulFile.transferTo(file); // 사용자가 원하는 위치에 파일을 생성

				fileMap = new HashMap<String, Object>();

				fileMap.put("BOARD_IDX", board_IDX);
				fileMap.put("ORIGINAL_FILE_NAME", original_Name);
				fileMap.put("STORED_FILE_NAME", stored_Name);
				fileMap.put("FILE_SIZE", mulFile.getSize());
				fileList.add(fileMap);
			}
		}
		return fileList;
	}

	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request)
			throws Exception {
		MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
		MultipartFile mulFile = null;

		String original_File_Name = null;
		String stored_File_Name = null;
		String original_Extension = null;

		Iterator<String> iterator = mulReq.getFileNames();
		String board_IDX = map.get("IDX").toString();
		String IDX = null;
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		Map<String, Object> fileMap = null;
		
		
		while (iterator.hasNext()) {
			mulFile = mulReq.getFile(iterator.next());

			if (mulFile.isEmpty() == false) {
				original_File_Name = mulFile.getOriginalFilename();
				original_Extension = original_File_Name.substring(original_File_Name.lastIndexOf("."));
				stored_File_Name = CommonUtils.getRandomString() + original_Extension;

				mulFile.transferTo(new File(filePath + stored_File_Name));

				fileMap = new HashMap<String, Object>();
				fileMap.put("BOARD_IDX", board_IDX);
				fileMap.put("ORIGINAL_FILE_NAME", original_File_Name);
				fileMap.put("STORED_FILE_NAME", stored_File_Name);
				fileMap.put("FILE_SIZE", mulFile.getSize());
				fileMap.put("IS_NEW", "Y");
				fileList.add(fileMap);
			} else {
				String requestName = mulFile.getName();
				IDX = "IDX_" + requestName.substring(requestName.indexOf("_") + 1);
				if (map.containsKey(IDX) == true && map.get(IDX) != null) {
					fileMap = new HashMap<String, Object>();
					fileMap.put("IS_NEW", "N");
					fileMap.put("FILE_IDX", map.get(IDX));
					fileList.add(fileMap);
				}
			}
		}
		return fileList;
	}
}
