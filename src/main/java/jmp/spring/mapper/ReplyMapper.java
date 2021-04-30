package jmp.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import jmp.spring.vo.Criteria;
import jmp.spring.vo.ReplyVo;

public interface ReplyMapper {
	
	public int insert(ReplyVo vo);
	public int update(ReplyVo vo);
	public int delete(int rno);
	public ReplyVo get(int rno);
	public List<ReplyVo> getList(@Param("bno") int bno,    //2개 이상은 @param으로정의 꼭
								 @Param("cri")Criteria cri);
	public int getTotal(int bno);
	
	//게시글의 댓글수를 카운트해서 replycnt칼럼에 입력
	public int updateReplyCnt(int bno); //1개는 그냥 넣어준다
}
