package jmp.spring.vo;

import java.util.UUID;

import lombok.Data;

@Data
public class AttachFileVo {

	private int attachNo;
	private String uuid;
	private String uploadPath;
	private String fileName; 
	private String fileType;
	private String regdate;

	public AttachFileVo(int attachNo, String uploadPath, String fileName) {
		super();
		
		UUID uuid = UUID.randomUUID();
		this.uuid = uuid.toString();
		this.fileType = "N";
		this.attachNo = attachNo;
		this.uploadPath = uploadPath;
		this.fileName = fileName;
		
		this.savePath = uploadPath + uuid + "_" + fileName;
		this.s_savePath = uploadPath + "s_" + uuid + "_" + fileName;
	}
	
	private String savePath;
	private String s_savePath;
	
}
