package jmp.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jmp.spring.mapper.BoardMapper;
import jmp.spring.vo.BoardVo;
import jmp.spring.vo.Criteria;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper mapper;
	
	public List<BoardVo> getList(Criteria cri) {
		return mapper.getList(cri);
	}

	@Override
	public int insertBoard(BoardVo vo) {
		return mapper.insertBoard(vo);
	}

	@Override
	public BoardVo get(int bno) {
		return mapper.get(bno);
	}

	@Override
	@Transactional
	public int update(BoardVo vo) {
		//게시글이 수정시 원래 게시글이 백업테이블에 저장된 후 수정된다.
		mapper.boardBackup(vo.getBno());
		return mapper.update(vo);
	}

	@Override
	public int delete(int bno) {
		return mapper.delete(bno);
	}

	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotal(cri);
	}

	
	

	

}
