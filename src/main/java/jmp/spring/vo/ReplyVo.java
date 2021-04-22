package jmp.spring.vo;

import lombok.Data;

@Data
public class ReplyVo {
	
	int rno;	//리플번호
	int bno;	//게시글번호
	String reply;	//리플
	String replyer;	//작성자
	String replydate;	//작성시간
	String updatedate;	//수정시간
}
