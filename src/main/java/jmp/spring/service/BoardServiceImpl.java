package jmp.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jmp.spring.mapper.BoardMapper;
import jmp.spring.vo.BoardVo;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper mapper;
	
	@Override
	public List<BoardVo> getList() {
		return mapper.getList();
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
	public int update(BoardVo vo) {
		return mapper.update(vo);
	}

	@Override
	public int delete(int bno) {
		return mapper.delete(bno);
	}

	
	

	

}
