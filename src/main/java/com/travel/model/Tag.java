package com.travel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Entity
@Table(name = "tags")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Tag implements Serializable {

	public Tag() {
	}

	public Tag(String displayText) {
		this.displayText = displayText;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7517669442672597146L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "displayText", unique = true, length = 80, nullable = false)
	@NotBlank
	@Size(max = 80)
	private String displayText;

	public Integer getId() {
		return id;
	}

	public String getDisplayText() {
		return displayText;
	}

}
