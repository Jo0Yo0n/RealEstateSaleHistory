package com.kosa.realestate.community.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@Table(name = "FileMetaData")
	public class FileMetaData {
		@Id
//		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int file_id;
		private int post_id;

		private String file_name;
		private String file_path;
		private String file_type;
		private int file_size;
		private String upload_date;

	

}
