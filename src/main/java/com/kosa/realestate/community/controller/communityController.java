package com.kosa.realestate.community.controller;

import java.io.File;
import java.io.IOException;
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

	@Autowired
	private ICommunityService communityService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IAdminDivisionService adminDivisionService;
	
	
	@PostMapping("/postUpdate")
	@ResponseBody
	// 게시글 업로드(아이디,구아이디,제목,내용)
	public void postUpdate(@RequestParam("userId") int userId, @RequestParam("title") String title,
			@RequestParam("districtId") int districtId,
			@RequestParam("content") String content, @RequestParam(value = "uploadFile", required = false) MultipartFile[] files) {
		
		//게시글 업로드
		PostDTO pdto = new PostDTO(userId, districtId, title, content);
		
		// insert한 idx값을 가져온다
		communityService.insertPost(pdto);
		int idx = pdto.getPostId(); 
		
		communityService.fileTest(idx,files);
		
	}
	
	
	// 커뮤니티 작성폼
	@GetMapping("/communityForm")
	public String communityForm(Principal principal, Model model) {
		
		String email = principal.getName();
		UserDTO udto = userService.findUserByEmail(email);
		model.addAttribute("userId", udto.getUserId());
		
		List<DistrictDTO> districts = adminDivisionService.findDistrictList(1L);
		model.addAttribute("districts", districts);
		
		return "communityForm";
	}

	// 커뮤니티 리스트
	@GetMapping("/communityList")
	public String communityList(Principal principal, Model model) {
		
		List<DistrictDTO> districts = adminDivisionService.findDistrictList(1L);

		model.addAttribute("districts", districts);
		
		List<PostDTO> postList = communityService.postList();

		model.addAttribute("postList", postList);

		return "communityList";
	}

	//게시판 상세
    @GetMapping("/communityCard")
    public String communityCard(@RequestParam("postId") int postId, Model model, Principal principal) {
  			
    	//글
        PostDTO commuCard = communityService.communityCard(postId);
        model.addAttribute("commuCard", commuCard);
        
    	//사용자 검증
		String email = principal.getName();
		UserDTO udto = userService.findUserByEmail(email);
		Long userId = udto.getUserId();
		
		boolean isAuthor = commuCard.getUserId() == userId.intValue();  
		model.addAttribute("isAuthor", isAuthor);
        
        //파일
        List<FileMetaDataDTO> files = communityService.selectFiles(postId);
        model.addAttribute("files",files);

        return "communityCard"; 
    }
    

	@GetMapping("/reply")
	public String reply() {
		return "reply";
	}
    
	//게시판 수정 폼
	@GetMapping("/commuUpdate")
	public String commuUpdate(@RequestParam("postId") int postId, Model model) {
		
	    PostDTO commuCard = communityService.communityCard(postId);
        model.addAttribute("commuCard", commuCard);
        
		boolean isAuthor = true; 
		model.addAttribute("update", isAuthor);
		
        List<FileMetaDataDTO> files = communityService.selectFiles(postId);
        model.addAttribute("files",files);
        System.out.println("files------"+files);
        
		List<DistrictDTO> districts = adminDivisionService.findDistrictList(1L);
		model.addAttribute("districts", districts);
		
		return "communityForm";
	}
	
	//커뮤니티 파일 삭제
	@ResponseBody
	@PostMapping("/deleteFile")
	private List<FileMetaDataDTO> deleteFile(@RequestParam("fileId") int fileId, @RequestParam("postId") int postId) {
		
		System.out.println("fileId!"+fileId);
		communityService.deleteFile(fileId);
        List<FileMetaDataDTO> files = communityService.selectFiles(postId);
		System.out.println(postId);
		
		return files;
	}
	
	//게시글 수정 등록 
	@ResponseBody
	@PostMapping("/updateCommu")
	public void updateCommu(@RequestParam("userId") int userId,@RequestParam("title") String title,
	                          @RequestParam("postId") int postId,
	                          @RequestParam("content") String content, @RequestParam("districtId") int districtId, @RequestParam(value = "uploadFile", required = false) MultipartFile[] files) {
		
		PostDTO pdto = new PostDTO();
		
		pdto.setUserId(userId);
		pdto.setTitle(title);
		pdto.setPostId(postId);
		pdto.setContent(content);
		pdto.setDistrictId(districtId);
		
		//게시글 업데이트
		communityService.postUpdate(pdto);
		
		//피일 업로드
		communityService.fileTest(postId,files);

	}
}
