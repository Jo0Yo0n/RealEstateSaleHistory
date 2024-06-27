package com.kosa.realestate.community.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kosa.realestate.admindivision.model.dto.DistrictDTO;
import com.kosa.realestate.admindivision.service.IAdminDivisionService;
import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;
import com.kosa.realestate.community.service.ICommunityService;
import com.kosa.realestate.users.model.UserDTO;
import com.kosa.realestate.users.service.IUserService;

@Controller
public class communityController {

	private String uploadFolder = "C:\\fileUpload";

	@Autowired
	private ICommunityService communityService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IAdminDivisionService adminDivisionService;
	
	
	@PostMapping("/postUpdate")
	@ResponseBody
	// 게시글 업로드(아이디,구아이디,제목,내용)
	public String postUpdate(@RequestParam("userId") int userId, @RequestParam("title") String title,
			@RequestParam("districtId") int districtId,
			@RequestParam("content") String content, @RequestParam(value = "uploadFile", required = false) MultipartFile[] files) {
		
		//게시글 업로드
		PostDTO pdto = new PostDTO(userId, districtId, title, content);
		// insert한 idx값을 가져온다
		communityService.insertPost(pdto);
		int idx = pdto.getPostId(); 
		
		if(files != null) {
			
			for (MultipartFile multipartFile : files) {
				if (multipartFile.isEmpty()) {
					continue;
				}
	
				String originalFileName = multipartFile.getOriginalFilename();
				
				//원본 파일명
				String fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1).substring(0, originalFileName.lastIndexOf("."));; //확인 O
				System.out.println("fileName"+fileName);
				//파일 사이즈
				long fileSize = multipartFile.getSize();
	
				UUID uuid = UUID.randomUUID();
				// UUID적용한 파일명
				String savelFileName = uuid.toString();
				
				// 서버에 저장된 전체 경로
				String filePath = uploadFolder + File.separator + makeFolder() + File.separator + savelFileName; 
				System.out.println("filePath"+filePath);
				//디렉토리 경로		
				String directoryPath = filePath.substring(0, filePath.lastIndexOf("\\") + 1);
				
				String fileType = originalFileName.substring(originalFileName.lastIndexOf(".") + 1); //파일 유형
				
				File saveFile = new File(filePath);
	
				try {
					FileMetaDataDTO fdto = new FileMetaDataDTO();
					fdto.setPostId(idx);//게시글 아이디
					fdto.setFileName(savelFileName);//파일명
					fdto.setFilePath(directoryPath);//폴더 경로
					fdto.setFileType(fileType);//파일 타입
					fdto.setFileOriginName(fileName);//원본 파일명
					fdto.setFileSize(fileSize);
					
					multipartFile.transferTo(saveFile); // 서버(폴더)에 파일 저장
					communityService.fileUpload(fdto);
				} catch (IOException e) {
					e.printStackTrace();
					return "File upload failed: " + originalFileName;
				}
			}
		}
		return "nn";
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

	
	// 커뮤니티 작성폼
	@GetMapping("/communityForm")
	public String communityForm(Principal principal, Model model) {
		
		String email = principal.getName();
		UserDTO udto = userService.findUserByEmail(email);
		
		List<DistrictDTO> districts = adminDivisionService.findDistrictList(1L);

		model.addAttribute("districts", districts);
		model.addAttribute("userId", udto.getUserId());
		
		return "communityForm";
	}

	// 커뮤니티 리스트
	@GetMapping("/communityList")
	public String communityList(Principal principal, Model model) {
		
		List<DistrictDTO> districts = adminDivisionService.findDistrictList(1L);

		model.addAttribute("districts", districts);
		
		List<PostDTO> postList = communityService.postList();
		System.out.println("커뮤니티 리스트"+postList);

		model.addAttribute("postList", postList);

		return "communityList";
	}

	//게시판 상세
    @GetMapping("/communityCard")
    public String communityCard(@RequestParam("postId") int postId, Model model) {
    	
        PostDTO commuCard = communityService.communityCard(postId);
        System.out.println("commuCard: " + commuCard);
        model.addAttribute("commuCard", commuCard);

        return "communityCard"; 
    }
    
    
	@GetMapping("/reply")
	public String reply() {
		return "reply";
	}

}
