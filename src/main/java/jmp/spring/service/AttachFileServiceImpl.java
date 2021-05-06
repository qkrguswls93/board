package jmp.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jmp.spring.mapper.AttachFileMapper;
import jmp.spring.mapper.ReplyMapper;
import jmp.spring.vo.AttachFileVo;
import jmp.spring.vo.Criteria;
import jmp.spring.vo.ReplyVo;

@Service
public class AttachFileServiceImpl implements AttachFileService{

	@Autowired
	AttachFileMapper mapper;
	@Override
	public int getSeq() {
		// TODO Auto-generated method stub
		return mapper.getSeq();
	}

	@Override
	public int insert(AttachFileVo vo) {
		// TODO Auto-generated method stub
		return mapper.insert(vo);
	}

	@Override
	public List<AttachFileVo> getList(int attachNo) {
		// TODO Auto-generated method stub
		return mapper.getList(attachNo);
	}

	@Override
	public int delete(String uuid, int attachNo) {
		// TODO Auto-generated method stub
		return mapper.delete(uuid, attachNo);
	}

	@Override
	public AttachFileVo get(String uuid, int attachNo) {
		// TODO Auto-generated method stub
		return mapper.get(uuid, attachNo);
	}
	
	

	
}
