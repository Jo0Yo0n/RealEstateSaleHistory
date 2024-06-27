package com.kosa.realestate.community.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
	public class FileMetaDataDTO {
	
	
		private int fileId;
		private int postId;
		private String fileName;
		private String filePath;
		private String fileType;
		private long fileSize;
		private String fileOriginName;
		private String uploadDate;
		
//		private int file_id;
//		private int post_id;
//		private String file_name;
//		private String file_path;
//		private String file_type;
//		private int file_size;
//		private String upload_date;

}



