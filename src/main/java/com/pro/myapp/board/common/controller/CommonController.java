package com.pro.myapp.board.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pro.myapp.board.common.common.CommandMap;
import com.pro.myapp.board.common.service.CommonService;


@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/common/downloadFile.do")
	public void downloadFile(CommandMap commandMap, HttpServletResponse response)throws Exception{
		Map<String, Object> map = commonService.selectFileInfo(commandMap.getMap());
		
		String original_File_Name = (String)map.get("original_file_name");
		
		String stored_File_Name = (String)map.get("stored_file_name");
		
		byte[] fileByte = FileUtils.readFileToByteArray(new File("C:\\spring\\file\\"+stored_File_Name));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(original_File_Name,"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
