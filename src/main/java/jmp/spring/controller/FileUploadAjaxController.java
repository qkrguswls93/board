package jmp.spring.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jdk.internal.org.jline.utils.Log;
import jmp.spring.service.AttachFileService;
import jmp.spring.vo.AttachFileVo;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Log4j
@RestController
public class FileUploadAjaxController {
	
	private static final String ROOT_DIR = "C:\\upload\\";
	
	public AttachFileService service;
	@PostMapping("/fileUploadAjax")
	public List<AttachFileVo> fileUpload(MultipartFile[] uploadFile, int attachNo) {
		
		log.info("attachNo========"+attachNo);
		//attachNo = 0이면 파일을 처음 등록하는거
		//신규건의경우 시퀀스번호 생성
		if(attachNo == 0) {
			attachNo = service.getSeq();
		}
		
		for(MultipartFile multipartfile : uploadFile) {
			log.info("=========================");
			log.info(multipartfile.getOriginalFilename());
			log.info(multipartfile.getName());
			log.info(multipartfile.getSize());
			log.info("=========================");
			
			
			//중복 방지를 위해 UUID를 생성하여 파일명 앞에 붙여준다.
			
			UUID uuid = UUID.randomUUID();
			
			String uploadPath = getFolder();
			
			String uploadFileName = 
					uuid.toString() + "_"
					+ multipartfile.getOriginalFilename();
			
			File saveFile = new File(ROOT_DIR + uploadPath + uploadFileName);
			try {
				multipartfile.transferTo(saveFile);
				
				String contentType = Files.probeContentType(saveFile.toPath());
				
				if(contentType.startsWith("image")) {
					String thumnail = ROOT_DIR+uploadPath+"s_" + uploadFileName;
					//썸네일 이미지 생성
					Thumbnails.of(saveFile).size(100, 100).toFile(thumnail);
				}
				
				//vo를 생성해서 파일정보를  DB에 인서트
				AttachFileVo vo = new AttachFileVo();			
				vo.setUuid(uuid.toString());
				vo.setAttachNo(attachNo);
				vo.setFileName(multipartfile.getOriginalFilename());
				vo.setFileType(contentType.startsWith("image")?"Y":"N");
				vo.setUploadPath(uploadPath);
				
				//파일정보를  DB에 저장한다
				service.insert(vo);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		//업로드된 파일리스트를 조회 화면에 출력
		List<AttachFileVo> list = service.getList(attachNo);
		return list;
	}
	
	/*
	 * 중복방지
	 * 업로드날짜를 년/월/일 업로드 경로로 지정
	 * 지정된 경로에 폴더가 없으면 생성
	 * @return uploadPath
	 */
	private String getFolder() {
		String uploadPath = "";
		
		//오늘날짜를 yyyy-MM-dd 에 맞게 String 값으로 가지고 온다
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(new Date());
		
		uploadPath = str.replace("-", File.separator) + File.separator;
		
		File saveFile = new File(ROOT_DIR + uploadPath);
		
		//경로가 없으면 생성
		if(!saveFile.exists()) {
			saveFile.mkdirs();
		}
		return uploadPath;
	}
}
