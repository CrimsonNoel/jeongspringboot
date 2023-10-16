package service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.Member;
import mybatis.MemberAnno;



@Repository
public class MemberMybatis {
	
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	public int insertMember(Member m) {
		return sqlSession.getMapper(MemberAnno.class).insertMember(m);
		
	}

	public Member oneMember(String id) {
		return sqlSession.getMapper(MemberAnno.class).oneMember(id);
	}
	
	public int updateMember(Member m) {
		return sqlSession.getMapper(MemberAnno.class).updateMember(m);
		
		

	}
	
	public int deleteMember(String id) {
		return sqlSession.getMapper(MemberAnno.class).deleteMember(id);
	}
	
	
	public int changePass(String id, String newPass) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("pass", newPass);
		return sqlSession.getMapper(MemberAnno.class).changePass(map);
	

	}
	
	
	public List<Member> memberList() {
		return sqlSession.getMapper(MemberAnno.class).memberList();
	}
	
}
