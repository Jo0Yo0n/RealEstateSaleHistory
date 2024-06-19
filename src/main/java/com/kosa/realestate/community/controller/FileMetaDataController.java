package com.kosa.realestate.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
public class FileMetaDataController {

    String uploadFolder = "C:\\fileUpload";

    @GetMapping("/community")
    public String name() {
        return "communityForm";
    }

    @PostMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("uploadFile") MultipartFile[] files) {
        for (MultipartFile multipartFile : files) {
            if (multipartFile.isEmpty()) {
                continue;
            }

            String originalFileName = multipartFile.getOriginalFilename();
//          System.out.println("파일 이름2"+originalFileName.substring(originalFileName.lastIndexOf("\\")+1)); //위에랑 차이가 없는데
            
            String filePath = uploadFolder + File.separator + makeFolder() + File.separator + originalFileName; // 서버에 저장된 전체 경로
            
            UUID uuid = UUID.randomUUID(); 
            originalFileName=uuid.toString()+"_"+originalFileName;	

            File saveFile = new File(filePath); 

            try {
                multipartFile.transferTo(saveFile); //서버(폴더)에 파일 저장
            } catch (IOException e) {
                e.printStackTrace();
                return "File upload failed: " + originalFileName;
            }
        }
        return "File upload success!";
    }

    // 폴더 생성
    private String makeFolder() {
    	
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadFolder, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }
}
