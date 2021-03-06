package jmp.spring.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jmp.spring.service.AttachFileService;
import jmp.spring.vo.AttachFileVo;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Log4j
@RestController
public class FileUploadAjaxController {
	
	private static final String ROOT_DIR = "C:\\upload\\";
	
	@Autowired
	public AttachFileService service;
	
	@GetMapping("/attachFileDelete/{uuid}/{attachNo}")
	public String delete(@PathVariable("uuid") String uuid, 
						 @PathVariable("attachNo") int attachNo) {
		
		AttachFileVo vo = service.get(uuid, attachNo);
		
		
		File file = new File(ROOT_DIR+vo.getSavePath());
		if(file.exists()) 
			file.delete();
		
		//이미지는 썸네일도 삭제
		if(vo.getFileType()=="Y") {
			File sfile = new File(ROOT_DIR+vo.getS_savePath());
			
			if(sfile.exists())
			sfile.delete();
		}
				
		int res = service.delete(uuid, attachNo);
		return res>0?"success":"fail";
	}
	
	@GetMapping("/download") // /download?fileName=파일이름
	public ResponseEntity<byte[]> download(String fileName) {
		log.info("/download============fileName : " + fileName);
		
		File file = new File(ROOT_DIR+fileName);
		log.info("파일여부======");
		if(file.exists()) {
			//파일을  responseentity 에 담아서 반환
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
				headers.add("Content-Disposition", "attachment;filename=\"" 
				             + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\""); //\는 문자이용하려고 쓰는거("")  new~이거는 인코딩처리임
					
				return new ResponseEntity<byte[]>(
							FileCopyUtils.copyToByteArray(file),
							headers, HttpStatus.OK);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	/*
	 * 이미지 파일의 경로를 받아서 이미지를 return
	 * @param fileName
	 */
	
	
	@GetMapping("/display")
	public ResponseEntity<byte[]> display(String fileName) {
		log.info("/display===============fileName : " + fileName);
		//fileName=uploadPath+uuid+_+filename
		// /dislplay?fileName=파일명
		
		File file = new File(ROOT_DIR + fileName);
		
		if(file.exists()) {
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", Files.probeContentType(file.toPath()));
				
				//  ResponseEntity객체에 파일과 헤더를 담아서 브라우저로 리턴
				return new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file)
							, headers, HttpStatus.OK);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			log.info("====================" + file.toPath());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
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
				log.info("aaaa"+contentType);
				 if(contentType != null && contentType.startsWith("image")) { 
					 Thumbnails.of(saveFile).size(100, 100).toFile(ROOT_DIR + vo.getS_savePath());
					 vo.setFileType("Y"); 
					 }
				 log.info("aaaaa"+contentType);
				 
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
