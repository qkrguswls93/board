package jmp.spring.mapper;

import java.util.List;

import jmp.spring.vo.AttachFileVo;

public interface AttachFileMapper {

	public int getSeq();
	public int insert(AttachFileVo vo);
	public List<AttachFileVo> getList(int attachNo);
}
