package service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.Board;
import model.Comment;



@Repository
public class BoardMybatis {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	
	
	
	
	private    static final String NS = "mybatis.board.";
	
	public int insertBoard(Board board) {
		return sqlSession.insert(NS+"insertBoard", board);
	}
	
	
	public int insertComment(String comment, int num) {
		Map map = new HashMap();
		map.put("comment", comment);
		map.put("num", num);
		return sqlSession.insert(NS+"insertComment", map);
		

	}
	
	public int boardCount(String boardid) {
		return sqlSession.selectOne(NS+"boardCount", boardid);	
	}
	
	

	public List<Board> boardList(int pageInt, int limit, String boardid) {
		Map map = new HashMap();
		map.put("boardid", boardid);
		map.put("start", (pageInt-1)*limit + 1);
		map.put("end", pageInt*limit);
		return sqlSession.selectList(NS+"boardList", map);	
		
			
	}
	
	
	
	
	public List<Comment> commentList(int num) {
		return sqlSession.selectList(NS+"commentList", num);		
	}
	
	
	public Board boardOne(int num) {
		return sqlSession.selectOne(NS+"boardOne", num);			
	}
	
	public int boardUpdate(Board board) {
		return sqlSession.insert(NS+"boardUpdate", board);
	

	}
	
	public int boardDelete(int num) {
		sqlSession.delete(NS+"commentDelete", num);
		return sqlSession.delete(NS+"boardDelete", num);
		
	}	
}  //end class
