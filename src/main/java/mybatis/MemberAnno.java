package mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import model.Member;

public interface MemberAnno {
	
	@Insert("insert into member values (#{id}, #{pass}, #{name}, "
			+ " #{gender}, #{tel}, #{email}, #{picture}, sysdate)")
	int insertMember(Member m);

	@Select ("select * from member where id = #{id}")
	Member oneMember(String id);
	
	@Update("update member set name=#{name}, gender=#{gender}, tel=#{tel}, "
			+ " email=#{email}, picture=#{picture} where id = #{id} ")
	int updateMember(Member m);
	
	
	@Delete("delete from member where id = #{id} ")
	int deleteMember(String id);
	
	@Update("update member set pass=#{pass}  where id = #{id} ")
	int changePass(Map map);
	
	@Select("select * from member")
	List<Member> memberList();
	
}
