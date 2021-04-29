package jmp.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jmp.spring.mapper.ReplyMapper;
import jmp.spring.vo.Criteria;
import jmp.spring.vo.ReplyVo;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
	ReplyMapper mapper;

	@Override
	@Transactional
	public int insert(ReplyVo vo) {
		int res = mapper.insert(vo);
		
		//댓글의 갯수는 댓글을 입력할때, 삭제할때 변경된다
		//댓글의 갯수를 카운트 해서  tbl_board테이블의   replycnt칼럼에 update
		//갯수가 변경되는 게시글의 번호는 vo.getBno();
		
		mapper.updateReplyCnt(vo.getBno());
		return res;
	}

	@Override
	public int update(ReplyVo vo) {
		int res = mapper.update(vo);
		//댓글수 카운트 하여 테이블에 저장
		mapper.updateReplyCnt(vo.getBno());
		return res;
	}

	@Override
	@Transactional
	public int delete(int rno) {
		//댓글 삭제시 댓글의 수가 변경됨->댓글수를 카운트
		//reply 상세정보 조회
		ReplyVo vo = mapper.get(rno);

		int res = mapper.delete(rno);
		//상세정보의 게시글번호에 해당하는 리플의 수를 카운트
		mapper.updateReplyCnt(vo.getBno());
		return res;
	}

	@Override
	public ReplyVo get(int rno) {
		return mapper.get(rno);
	}

	@Override
	public List<ReplyVo> getList(int bno, Criteria cri) {
		return mapper.getList(bno, cri);
	}

	@Override
	public int getTotal(int bno) {
		return mapper.getTotal(bno);
	}

	
}
