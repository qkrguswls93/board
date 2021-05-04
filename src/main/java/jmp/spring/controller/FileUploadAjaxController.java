package jmp.spring.controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	AttachFileService service;
	
	@GetMapping("/display")
	/*
	 * 이미지 파일의 경로를 받아서 이미지를 return
	 * @param fileName
	 */
	
	public ResponseEntity<byte[]> display(String fileName) {
		log.info("/display===============fileName : " + fileName);
		//fileName=uploadPath+uuid+_+filename
		File file = new File(ROOT_DIR + fileName);
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		if(file.exists()) {
			try {
				headers.add("Content-Type", Files.probeContentType(file.toPath()));
				
				return new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file)
							, headers, HttpStatus.OK);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/*
	 * 파일 리스트 조회
	 */
	@GetMapping("/getFileList/{attachNo}")
	public List<AttachFileVo> getList(@PathVariable("attachNo") int attachNo){
		return service.getList(attachNo);
	}
	
	@PostMapping("/fileUploadAjax")
	public Map<String, String> fileUpload(MultipartFile[] uploadFile, int attachNo) {
		
		Map<String, String> map = new HashMap<String, String>();
		log.info("attachNo========"+attachNo);
		//attachNo = 0이면 파일을 처음 등록하는거
		//신규건의경우 시퀀스번호 생성
		if(attachNo == 0) {
			attachNo = service.getSeq();
		}
		
		int res = 0;
		
		for(MultipartFile multipartFile : uploadFile) {
			
			AttachFileVo vo = new AttachFileVo(attachNo, getFolder(), multipartFile.getOriginalFilename());
			
			try {
				File saveFile = new File(ROOT_DIR + vo.getSavePath());
				
				multipartFile.transferTo(saveFile);
				
				//Mime 타입을 모를 경우=null반환(ex- sql파일)
				String contentType = Files.probeContentType(saveFile.toPath());
				
				 if(contentType != null && contentType.startsWith("image")) { 
					 Thumbnails.of(saveFile).size(100, 100).toFile(ROOT_DIR + vo.getSavePath());
					 vo.setFileType("Y"); 
					 }
				 
				//파일 정보를 DB에 저장
				if(service.insert(vo)>0) {
					res++;
				}
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		map.put("attachNo", attachNo + "");
		map.put("result", res + "건 저장");
		return map;
		}
	
	/*
	 * 중복방지
	 * 업로드날짜를 년/월/일 업로드 경로로 지정
	 * 지정된 경로에 폴더가 없으면 생성
	 * @return uploadPath
	 */
	private String getFolder() {
		
		//오늘날짜를 yyyy-MM-dd 에 맞게 String 값으로 가지고 온다
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(new Date());
		
		String uploadPath = str.replace("-", File.separator) + File.separator;
		log.info("=============" + uploadPath);
		
		File saveFile = new File(ROOT_DIR + uploadPath);
		//디렉토리생성
		if(!saveFile.exists()) {
			saveFile.mkdirs();
		}
		return uploadPath;
	}
}
