package controller;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import model.Board;
import service.BoardMybatis;
import model.Comment;
/*
 * REST란
REST의 정의
“Representational State Transfer” 의 약자
자원을 이름(자원의 표현)으로 구분하여 해당 자원의 상태(정보)를 주고 받는 모든 것을 의미한다.
즉, 자원(resource)의 표현(representation) 에 의한 상태 전달
자원(resource)의 표현(representation)
자원: 해당 소프트웨어가 관리하는 모든 것
-> Ex) 문서, 그림, 데이터, 해당 소프트웨어 자체 등
자원의 표현: 그 자원을 표현하기 위한 이름
-> Ex) DB의 학생 정보가 자원일 때, ‘students’를 자원의 표현으로 정한다.
상태(정보) 전달
데이터가 요청되어지는 시점에서 자원의 상태(정보)를 전달한다.
JSON 혹은 XML를 통해 데이터를 주고 받는 것이 일반적이다.
월드 와이드 웹(www)과 같은 분산 하이퍼미디어 시스템을 위한 소프트웨어 개발 아키텍처의 한 형식
REST는 기본적으로 웹의 기존 기술과 HTTP 프로토콜을 그대로 활용하기 때문에 웹의 장점을 최대한 활용할 수 있는 아키텍처 스타일이다.
REST는 네트워크 상에서 Client와 Server 사이의 통신 방식 중 하나이다.
REST의 구체적인 개념
HTTP URI(Uniform Resource Identifier)를 통해 자원(Resource)을 명시하고, HTTP Method(POST, GET, PUT, DELETE)를 통해 해당 자원에 대한 CRUD Operation을 적용하는 것을 의미한다.
즉, REST는 자원 기반의 구조(ROA, Resource Oriented Architecture) 설계의 중심에 Resource가 있고 HTTP Method를 통해 Resource를 처리하도록 설계된 아키텍쳐를 의미한다.
웹 사이트의 이미지, 텍스트, DB 내용 등의 모든 자원에 고유한 ID인 HTTP URI를 부여한다.
CRUD Operation
Create : 생성(POST)
Read : 조회(GET)
Update : 수정(PUT)
Delete : 삭제(DELETE)
HEAD: header 정보 조회(HEAD)
REST의 장단점
https://gmlwjd9405.github.io/2018/09/21/rest-and-restful.html
 *
 */
@RestController
@RequestMapping("/restboard/")
public class BoardRestController  {

	@Autowired
	BoardMybatis  bd;
	
	Model m;
	HttpSession session;
	HttpServletRequest request;
	
	//초기화 작업을 한다, 객체 초기화시에 사용한다 
	@ModelAttribute
	void init(HttpServletRequest request, Model m) {
		this.request=request;
		this.m=m;
		session = request.getSession();
		
	}
	

	@RequestMapping("boardForm")
	public String boardForm() {

		return "board/boardForm";
	}


	@RequestMapping("boardPro")
	public String boardPro(@RequestParam("f") MultipartFile  multipartFile, Board board) {
		String path = request.getServletContext().getRealPath("/") + "WEB-INF/view/board/images/";
		String msg = "게시물 등록 실패";
		String url = "/board/boardForm";
		
		String boardid = (String) session.getAttribute("boardid");
		if (boardid == null)	boardid = "1";
		String filename = " ";
		
		if (!multipartFile.isEmpty()) {
			File file = new File(path, multipartFile.getOriginalFilename());
			filename = multipartFile.getOriginalFilename();
			
				try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}	
		
		board.setFile1(filename);
		board.setBoardid(boardid);
		System.out.println(board);
		int num = bd.insertBoard(board);
		if (num > 0) {
			msg = "게시물 등록 성공";
			url = "/board/boardList";

			}

		m.addAttribute("msg", filename + ":" + msg);
		m.addAttribute("url", url);
		return "alert";
	}

	
	@RequestMapping("boardList")
	public List<Board> boardList() {
		
		
		// boardid 파라메터로 넘어 왔을때만 session에 저장 한다
		if (request.getParameter("boardid") != null) {
			session.setAttribute("boardid", request.getParameter("boardid"));
			session.setAttribute("pageNum", "1");
		}
		String boardid = (String) session.getAttribute("boardid");
		if (boardid == null)
			boardid = "1";
		
		// pageNum 파라메터로 넘어 왔을때만 session에 저장 한다
		if (request.getParameter("pageNum") != null) {
			session.setAttribute("pageNum", request.getParameter("pageNum"));
		}
		String pageNum = (String) session.getAttribute("pageNum");
		if (pageNum == null)
			pageNum = "1";
		
		
		int limit = 5; // 한 page당 게시물 갯수
		
		int pageInt = Integer.parseInt(pageNum); // page 번호
		
		int boardCount = bd.boardCount(boardid); // 전체 게시물 갯수
		int boardNum = boardCount - ((pageInt - 1) * limit);

		List<Board> list = bd.boardList(pageInt, limit, boardid);
		String boardName = "";
		switch (boardid) {
		case "1":
			boardName = "공지사항";
			break;
		case "2":
			boardName = "자유게시판";
			break;
		case "3":
			boardName = "QnA";
			break;

		}
		// pagging
		int bottomLine = 3;
		int start = (pageInt - 1) / bottomLine * bottomLine + 1;
		int end = start + bottomLine - 1;
		int maxPage = (boardCount / limit) + (boardCount % limit == 0 ? 0 : 1);
		if (end > maxPage)
			end = maxPage;

		m.addAttribute("bottomLine", bottomLine);
		m.addAttribute("start", start);
		m.addAttribute("end", end);
		m.addAttribute("maxPage", maxPage);
		m.addAttribute("pageInt", pageInt);
		m.addAttribute("list", list);
		m.addAttribute("boardNum", boardNum);
		m.addAttribute("boardName", boardName);

		return list; 
	}
	
	@RequestMapping("boardComment")
	public String boardComment(@RequestParam("num") int num) {
		Board board = bd.boardOne(num);
		List<Comment> commentLi = bd.commentList(num);
		m.addAttribute("board", board);
		m.addAttribute("commentLi", commentLi);
		return "board/boardComment";
	}
	
	@RequestMapping("boardCommentPro")
	public String boardCommentPro(@RequestParam("num") int boardnum) {
		
		String comment = request.getParameter("comment");
	
		int num = bd.insertComment(comment, boardnum);
		
		if (num==0) comment="저장되지 않았습니다 ";
		
		Comment c = new Comment();
		c.setContent(comment);
		c.setRegdate(new Date());
		
		m.addAttribute("c", c);
	
		return "board/boardCommentPro";
	}
	
	@RequestMapping("boardUpdateForm")
	public String boardUpdateForm(@RequestParam("num") int num) {
		
		String boardid = (String) request.getSession().getAttribute("boardid");
		if (boardid == null)  boardid="1";
		
		String boardName="";
		switch (boardid) {
		case "1":
			boardName = "공지사항";
			break;
		case "2":
			boardName = "자유게시판";
			break;
		case "3":
			boardName = "QnA";
			break;

		}
		
		
		
		
		
		
		Board board = bd.boardOne(num);
		m.addAttribute("board", board);
		m.addAttribute("boardName", boardName);
		return "board/boardUpdateForm";
	}
	

	@RequestMapping("boardUpdatePro")
	public String boardUpdatePro(@RequestParam("f") MultipartFile  multipartFile, Board board) {
		String path = request.getServletContext().getRealPath("/") + "WEB-INF/view/board/images/";
		String msg = "";
		String url = "";	
		String filename = " ";
		if (!multipartFile.isEmpty()) {
			File file = new File(path, multipartFile.getOriginalFilename());
			filename = multipartFile.getOriginalFilename();
			
				try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		board.setFile1(filename);	
			
			System.out.println(board);
		
			Board dbboard = bd.boardOne(board.getNum()); //pass 확인용  ======
			
			if(board.getPass().equals(dbboard.getPass())) {  //수정 가능 확인
				if (bd.boardUpdate(board)>0) { //update ok
				 msg="수정 완료";
				 url = "/board/boardComment?num="+board.getNum();
				} else {
					 msg="수정 실패";
					 url = "/board/boardUpdateForm?num="+board.getNum();
				}
				
			} else {
				
				 msg="비밀번호가 틀렸습니다";
				 url = "/board/boardUpdateForm?num="+board.getNum();
			}
			
			
		//} catch (Exception e) { e.printStackTrace(); }
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "alert";  //view/board/alert.jsp 확인
	}

	
	
	
	@RequestMapping("boardDeleteForm")
	public String boardDeleteForm(@RequestParam("num") int num) {
		
		//int num = Integer.parseInt(request.getParameter("num"));
		m.addAttribute("num", num);
		return "board/boardDeleteForm";
	}
	

	@RequestMapping("boardDeletePro")
	public String boardDeletePro(@RequestParam("num") int num, String pass) {
		
		Board dbboard = bd.boardOne(num);
		String msg="";
		String url = "";
		if (pass.equals(dbboard.getPass())) {
			if (bd.boardDelete(num) > 0) {
				msg="게시글이 삭제 되었습니다";
				url="/board/boardList";
			} else {
				msg="삭제 실패 입니다";
				url="/board/boardDeleteForm";}
		} else {
			msg="비밀번호 확인하세요";
			url="/board/boardDeleteForm";}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "alert";
	}	
	

}
