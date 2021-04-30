package jmp.spring.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.log4j.Log4j;

@Log4j
@RestController
public class FileUploadAjaxController {
	
	@PostMapping("/fileUploadAjax")
	public void fileUpload(MultipartFile[] uploadFile) {
		for(MultipartFile multipartfile : uploadFile) {
			log.info("=========================");
			log.info(multipartfile.getOriginalFilename());
			log.info(multipartfile.getName());
			log.info(multipartfile.getSize());
			log.info("=========================");
			
			//파일을 서버에 저장
			File saveFile = new File(multipartfile.getOriginalFilename());
			try {
				multipartfile.transferTo(saveFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
