package com.travel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cities")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4136823032299398200L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", unique = true, length = 80, nullable = false)
	@NotBlank
	@Size(max = 80)
	private String name;

	@Column(name = "country", length = 80, nullable = false)
	@NotBlank
	@Size(max = 80)
	private String country;

	@Column(name = "abouttext", length = 255, nullable = false)
	@NotBlank
	@Size(max = 255)
	private String abouttext;

	@Column(name = "howtoreach", length = 255, nullable = false)
	@NotBlank
	@Size(max = 255)
	private String howtoreach;

	@ManyToMany
	@JoinTable(name = "city_tags", joinColumns = @JoinColumn(name = "tag_id"), inverseJoinColumns = @JoinColumn(name = "city_id"))
	private Collection<Tag> tags = new ArrayList<>();

	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
	@OrderBy("popularity asc")
	private List<Attraction> attractions = new ArrayList<>();*/

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getAbouttext() {
		return abouttext;
	}

	public String getHowtoreach() {
		return howtoreach;
	}

	public Collection<Tag> getTags() {
		if (tags == null) {
			tags = new ArrayList<Tag>();
		}
		return tags;
	}

	/*public List<Attraction> getAttractions() {
		return attractions;
	}*/

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setAbouttext(String abouttext) {
		this.abouttext = abouttext;
	}

	public void setHowtoreach(String howtoreach) {
		this.howtoreach = howtoreach;
	}

}
