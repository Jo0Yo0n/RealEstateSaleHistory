package com.kosa.realestate.community.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * FileMetaDataDTO 클래스
 *
 * @author 강희원
 */

@Getter
@Setter
@ToString

	public class FileMetaDataDTO {
	
	
		private int fileId;
		private Long postId;
		private String fileName;
		private String filePath;
		private String fileType;
		private long fileSize;
		private String fileOriginName;
		private String uploadDate;
		

}



