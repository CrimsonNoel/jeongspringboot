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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import model.Board;
import service.BoardMybatis;
import model.Comment;

@Controller
@RequestMapping("/board/")
public class BoardController  {

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
	public String boardList() {
		
		
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

		return "board/boardList"; // WEB-INF/view/ board/boardList .jsp
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
