package com.travel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Entity
@Table(name="attachments")
@JsonAutoDetect(fieldVisibility = Visibility.ANY,getterVisibility=Visibility.NONE,setterVisibility=Visibility.NONE)
public class Attachment implements Serializable {
		
		@Id
		@Column(name="id")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name="size")
		private long size;
		
		@Column(name="contentType")
		private String contentType;
		
		@Column(name="fileName")
		private String fileName;

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public Integer getId() {
			return id;
		}
}
