package jmp.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jmp.spring.mapper.ReplyMapper;
import jmp.spring.vo.Criteria;
import jmp.spring.vo.ReplyVo;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
	ReplyMapper mapper;

	@Override
	public int insert(ReplyVo vo) {
		return mapper.insert(vo);
	}

	@Override
	public int update(ReplyVo vo) {
		return mapper.update(vo);
	}

	@Override
	public int delete(int rno) {
		return mapper.delete(rno);
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
