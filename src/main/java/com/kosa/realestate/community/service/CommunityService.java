package com.kosa.realestate.community.service;



import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kosa.realestate.community.dto.FileMetaDataDTO;
import com.kosa.realestate.community.dto.PostDTO;
import com.kosa.realestate.community.dto.PostInfoDTO;
import com.kosa.realestate.community.repository.CommunityRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CommunityService implements ICommunityService {

	private final CommunityRepository communityRepository;

	private String uploadFolder = "C:\\fileUpload";


	@Override
	public int insertPost(PostDTO pdto) {
		int generatedPostId = communityRepository.insertPost(pdto);
		return generatedPostId;
	}

	@Override
	public void fileUpload(FileMetaDataDTO fdto) {
		communityRepository.fileUpload(fdto);
	}

	@Override
	public List<PostDTO> postList() {
		List<PostDTO> postList = communityRepository.postList();
		return postList;
	}

	@Override
	public PostDTO communityCard(Long postId) {
		PostDTO commuCard = communityRepository.communityCard(postId);
		return commuCard;

	}



	// 게시글 조회
	public void findPostInfo(Long postId) {

		PostInfoDTO postInfo = communityRepository.selectPostInfo(postId);

		if (postInfo == null) {
			throw new RuntimeException("해당 되는 게시판이 없습니다.");
		}
	}

	@Override
	public List<FileMetaDataDTO> selectFiles(Long postId) {
		System.out.println("서비스" + postId);
		List<FileMetaDataDTO> files = communityRepository.selectFiles(postId);
		System.out.println("서비스" + files);
		return files;
	}

	@Override
	public void deleteFile(int fileId) {
		communityRepository.deleteFile(fileId);

	}


	public void fileTest(Long idx, MultipartFile[] files) {
		if (files != null) {

			for (MultipartFile multipartFile : files) {
				if (multipartFile.isEmpty()) {
					continue;
				}

				String originalFileName = multipartFile.getOriginalFilename();

				// 원본 파일명
				String fileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1)
						.substring(0, originalFileName.lastIndexOf("."));; // 확인 O
				System.out.println("fileName" + fileName);
				// 파일 사이즈
				long fileSize = multipartFile.getSize();

				UUID uuid = UUID.randomUUID();
				// UUID적용한 파일명
				String savelFileName = uuid.toString();

				// 서버에 저장된 전체 경로
				String filePath =
						uploadFolder + File.separator + makeFolder() + File.separator + savelFileName;
				System.out.println("filePath" + filePath);
				// 디렉토리 경로
				String directoryPath = filePath.substring(0, filePath.lastIndexOf("\\") + 1);

				String fileType = originalFileName.substring(originalFileName.lastIndexOf(".") + 1); // 파일
				// 유형

				File saveFile = new File(filePath);

				try {
					FileMetaDataDTO fdto = new FileMetaDataDTO();
					fdto.setPostId(idx);// 게시글 아이디
					fdto.setFileName(savelFileName);// 파일명
					fdto.setFilePath(directoryPath.replaceFirst("^C:\\\\fileUpload\\\\", ""));
					fdto.setFileType(fileType);// 파일 타입
					fdto.setFileOriginName(fileName);// 원본 파일명
					fdto.setFileSize(fileSize);

					multipartFile.transferTo(saveFile); // 서버(폴더)에 파일 저장
					fileUpload(fdto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

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


	// 게시글 삭제
	@Override
	public void postDelete(Long postId) {
		communityRepository.postDelete(postId);

	}

	@Override
	public List<PostDTO> loadPostsByDistrict(int districtId) {
		List<PostDTO> filterPostList = communityRepository.loadPostsByDistrict(districtId);
		return filterPostList;
	}

	@Override
	public void updateViewCount(Long postId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int visitor = 0;
			if (request.getCookies() != null) {
				Cookie[] cookies = request.getCookies();

				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("visitor")) {
						visitor = 1;
						if (cookie.getValue().contains(String.valueOf(postId))) {
							// 쿠키 값에 해당 게시물 번호가 포함되어 있을 때 수행할 작업
						} else {
							// 쿠키에 게시글 값 넣기
							cookie.setValue("_" + String.valueOf(postId));
							response.addCookie(cookie);

							communityRepository.updateViewCount(postId);
						}
					}
				}
			}
			if (visitor == 0) {
				Cookie cookie1 = new Cookie("visitor", String.valueOf(postId));
				response.addCookie(cookie1);
				communityRepository.updateViewCount(postId);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@Override
	public List<PostDTO> loadPostsByView(int districtId) {
		List<PostDTO> postList = communityRepository.loadPostsByView(districtId);
		return postList;
	}

	@Override
	public List<PostDTO> postViewList() {
		List<PostDTO> postList = communityRepository.postViewList();
		return postList;
	}

	@Override
	public void postUpdate(PostDTO pdto) {
		communityRepository.postUpdate(pdto);

	}

	public List<PostInfoDTO> findNewPostList() {

		return communityRepository.selectNewPostList();
	}

	// userId로 회원이 작성한 게시글 전체 조회
	public List<UserPostDTO> getPostsByUserId(Long userId, int page, int pageSize) {
		int offset = page * pageSize;
		return communityRepository.getPostsByUserId(userId, offset, pageSize);
	}

	// userId로 회원이 작성한 게시글 갯수 조회
	public int getTotalPostsCountByUserId(Long userId) {
		return communityRepository.getTotalPostsCountByUserId(userId);
	}
}