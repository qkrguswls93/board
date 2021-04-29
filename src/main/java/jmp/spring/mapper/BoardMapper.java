package jmp.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import jmp.spring.vo.BoardVo;
import jmp.spring.vo.Criteria;

public interface BoardMapper {
	
	@Select("select sysdate from dual") 
	public String getTime(); 
	
	public String getTime2();
	
	public List<BoardVo> getList(Criteria cri); //사용하기 위해 매개변수로 넣어준다-데이터 조회가능
	
	public int insertBoard(BoardVo vo); 
	
	public BoardVo get(int bno);
	
	//업데이트,작성,삭제는  int반환
	public int update(BoardVo vo);
	
	public int delete(int bno);
	
	public int getTotal(Criteria cri);
	
	public int boardBackup(int bno);
}
