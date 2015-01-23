package com.travel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.persistence.Transient;
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

	@ManyToMany
	@JoinTable(name = "city_tags", joinColumns = @JoinColumn(name = "tag_id"), inverseJoinColumns = @JoinColumn(name = "city_id"))
	private Collection<Tag> tags = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("popularity asc")
	private List<Attraction> attractions = new ArrayList<>();

	@Transient
	private List<String> nearByCityList;

	@Column(name = "nearByCities", length = 255, nullable = true)
	@Size(max = 255)
	private String nearByCities;

	// Connectivity
	@Column(name = "byFlight", length = 255, nullable = true)
	@NotBlank
	@Size(max = 255)
	private String byFlight;

	@Column(name = "byTrain", length = 255, nullable = true)
	@NotBlank
	@Size(max = 255)
	private String byTrain;

	@Column(name = "byRoad", length = 255, nullable = true)
	@NotBlank
	@Size(max = 255)
	private String byRoad;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="city_attachments", joinColumns=@JoinColumn(name="city_id",unique=false), inverseJoinColumns=@JoinColumn(name="attachment_id"))
	private Collection<Attachment> attachments= new ArrayList<Attachment>();

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

	public Collection<Tag> getTags() {
		if (tags == null) {
			tags = new ArrayList<Tag>();
		}
		return tags;
	}

	public List<Attraction> getAttractions() {
		return attractions;
	}

	public List<String> getNearByCityList() {
		if (nearByCityList == null) {
			return new ArrayList<String>();
		}
		return nearByCityList;
	}

	public String getNearByCities() {
		return nearByCities;
	}

	public String getByFlight() {
		return byFlight;
	}

	public String getByTrain() {
		return byTrain;
	}

	public String getByRoad() {
		return byRoad;
	}

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

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}

	public void setAttractions(List<Attraction> attractions) {
		this.attractions = attractions;
	}

	public void setNearByCityList(List<String> nearByCityList) {
		this.nearByCityList = nearByCityList;
		// Setup nearbycities
		String nearByCities = "";
		if (nearByCityList != null) {
			nearByCities = nearByCityList.stream().collect(
					Collectors.joining(","));
		}
		this.nearByCities = nearByCities;

	}

	public void setNearByCities(String nearByCities) {
		this.nearByCities = nearByCities;
		if (nearByCities != null) {
			this.nearByCityList = Arrays.asList(nearByCities.split(","));
		}
	}

	public void setByFlight(String byFlight) {
		this.byFlight = byFlight;
	}

	public void setByTrain(String byTrain) {
		this.byTrain = byTrain;
	}

	public void setByRoad(String byRoad) {
		this.byRoad = byRoad;
	}
	
	public Collection<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Collection<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(Attachment attachment) {
		attachments.add(attachment);
	}
}
