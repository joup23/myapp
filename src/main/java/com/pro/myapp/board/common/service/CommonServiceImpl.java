package com.pro.myapp.board.common.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pro.myapp.board.common.dao.CommonDAO;
import com.pro.myapp.board.common.service.CommonService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Resource(name="commonDAO")
	private CommonDAO commonDAO;
	
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return commonDAO.selectFileInfo(map);
	}

}
