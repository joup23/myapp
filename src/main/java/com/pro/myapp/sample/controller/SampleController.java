package com.pro.myapp.sample.controller;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.ExpandVetoException;

import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pro.myapp.board.common.common.CommandMap;
import com.pro.myapp.sample.service.SampleService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class SampleController {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "sampleService") // 빈을 주입
	private SampleService sampleService;

	@RequestMapping(value = "/testInterceptor.do")
	public ModelAndView testInterceptor(Map<String, Object> commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("");
		log.debug("인터셉터 테스트");

		return mv;
	}

	/*//페이징 전
	@RequestMapping(value = "/sample/openBoardList.do")
	public ModelAndView openBoardList(Map<String, Object> commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardList"); // 뷰페이지인 jsp 파일을 뜻함, sample/boardList.jsp 파일을 말함
		List<Map<String, Object>> list = sampleService.selectBoardList(commandMap); // samplService의
																					// selectBoardList메소드로부터
																					// 게시글 리스트를 가져온다 게시글은 Map으로 저장되며
																					// Map들을 List별로 묶은 것
		mv.addObject("list", list); // 서비스로직의 결과를 ModelAndView 객체에 담아서 클라이언트,즉 jsp에서 그결과를 사용할수 있도록 한다. list라는 키값

		return mv;
	}
	*/
	//페이징 기법
	@RequestMapping(value = "/sample/openBoardList.do")
	public ModelAndView openBoardList(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardList");
		Map<String, Object> map = sampleService.selectBoardList(commandMap.getMap());
		mv.addObject("list", map.get("result"));
		mv.addObject("paginationInfo",(PaginationInfo)map.get("paginationInfo"));
		
		return mv;
	}
	@RequestMapping(value = "/sample/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardWrite");

		return mv;
	}

	@RequestMapping(value = "/sample/writeBoard.do")
	public ModelAndView writeBoard(CommandMap commandMap, HttpServletRequest request) throws Exception {//전송된 파일정보를 담기위해 Http
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");// 게시글 등록이 완료되고 게시글 보기로 돌아간다
		sampleService.writeBoard(commandMap.getMap(),request);

		Enumeration test = request.getParameterNames();
		System.out.println("sampleController requet -------------------------------------");
		while (test.hasMoreElements()) {
			String name = (String)test.nextElement();
			System.out.println(name+" : "+request.getParameter(name));
		}
		System.out.println("--------------------------------------");
		
		return mv;
	}

	@RequestMapping(value = "/sample/openBoardDetail.do")
	public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception { // 리스트보기에서 선택한 IDX를 파라미터로 추가하여 넘겨줫기때문에
																					// commandMap
		ModelAndView mv = new ModelAndView("/sample/boardDetail");
		Map<String, Object> map = sampleService.selectBoard(commandMap.getMap());

		mv.addObject("map", map.get("map"));
		mv.addObject("list",map.get("list"));
		mv.addObject("comment",map.get("comment"));
		return mv;
	}

	@RequestMapping(value = "/sample/openBoardModify.do")
	public ModelAndView openBoardModify(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/sample/boardModify");
		Map<String, Object> map = sampleService.selectBoard(commandMap.getMap());
		
		mv.addObject("map", map.get("map"));
		mv.addObject("list",map.get("list"));
		
		System.out.println("### SampleCon.openBoardModi : "+map);	//제대로 받음
		return mv;
	}

	@RequestMapping(value = "/sample/modifyBoard.do")
	public ModelAndView modifyBoard(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		
		Enumeration test = request.getParameterNames();
		System.out.println("sampleController requet -------------------------------------");
		while (test.hasMoreElements()) {
			String name = (String)test.nextElement();
			System.out.println(name+" : "+request.getParameter(name));
		}
		System.out.println("--------------------------------------");
		
		sampleService.modifyBoard(commandMap.getMap(),request);
		mv.addObject("IDX", commandMap.get("IDX"));
		return mv;
	}

	@RequestMapping(value="/sample/deleteBoard.do")
	public ModelAndView deletBoard(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		sampleService.deleteBoard(commandMap.getMap());
		;
		return mv;
	}
	
	@RequestMapping(value="/sample/deleteBoardList.do")
	public ModelAndView deleteBoardList(Map<String, Object> commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("sample/boardDeleteList");
		List<Map<String, Object>> list = sampleService.selectBoardDeleteList(commandMap);
		
		mv.addObject("list",list);
		return mv;
	}
	
	@RequestMapping(value="/sample/restoreBoard.do")
	public ModelAndView restoreBoard(CommandMap commandMap)throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/deleteBoardList.do");
		sampleService.restoreBoard(commandMap.getMap());
		mv.addObject("IDX",commandMap.get("IDX"));
		return mv;
	}
	
	@RequestMapping(value = "/testCoustomMapArgumentResulver.do")
	public ModelAndView testCommnadMap(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("");

		if (commandMap.isEmpty() == false) {
			Iterator<Entry<String, Object>> iterator = commandMap.getMap().entrySet().iterator();
			Entry<String, Object> entry = null;

			while (iterator.hasNext()) {
				entry = iterator.next();
				log.debug("key : " + entry.getKey() + " , values : " + entry.getValue());
			}
		}
		return mv;
	}

	@RequestMapping(value="/sample/writeComment.do")
	public ModelAndView writeComment(CommandMap commandMap)throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		sampleService.writeComment(commandMap.getMap());
		
		mv.addObject("IDX",commandMap.get("IDX"));
		
		return mv;
	}
	
	@RequestMapping(value="/sample/deleteComment.do")
	public ModelAndView deleteComment(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		sampleService.deleteComment(commandMap.getMap());
		
		mv.addObject("IDX",commandMap.get("IDX"));
		return mv;
	}
	@RequestMapping(value="/sample/updateComment.do")
	public ModelAndView modifyComment(CommandMap commandMap)throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		sampleService.modifyComment(commandMap.getMap());
		
		mv.addObject("IDX",commandMap.get("IDX"));
		
		return mv;
	}
	
	@RequestMapping(value="/sample/openUser.do")
	public ModelAndView openUserJoin(CommandMap commandMap)throws Exception{
		ModelAndView mv = new ModelAndView("user/userJoin");
		
		mv.addObject("error", commandMap.get("error"));
		
		return mv;
	}
	@RequestMapping(value="/sample/joinUser.do")
	public ModelAndView joinUser(CommandMap commandMap)throws Exception{
		String error = sampleService.joinUser(commandMap.getMap());
		ModelAndView mv = null;
		if(error.equals("완료")) {
			mv = new ModelAndView("redirect:/sample/openBoardList.do");
		}else {
			mv = new ModelAndView("redirect:/sample/openUserJoin.do");
			mv.addObject("error",error);
		}
		return mv;
	}
}
