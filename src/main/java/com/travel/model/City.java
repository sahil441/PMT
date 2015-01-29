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
import javax.persistence.Lob;
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

	@Column(name = "abouttext", nullable = false)
	@NotBlank
	@Size(max = 500)
	@Lob
	private String abouttext;

	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("popularity asc")
	private List<Attraction> attractions = new ArrayList<>();

	@Transient
	private List<String> nearByCityList;

	@Column(name = "nearByCities", length = 255, nullable = true)
	@Size(max = 255)
	private String nearByCities;

	// Connectivity
	@Column(name = "closestAirport", length = 255, nullable = true)
	@NotBlank
	@Size(max = 255)
	private String closestAirport;
	
	@Column(name = "distanceFromAirport", nullable = true)
	private float distanceFromAirport;

	@Column(name = "closestRailwayStation", length = 255, nullable = true)
	@NotBlank
	@Size(max = 255)
	private String closestRailwayStation;
	
	@Column(name = "distanceFromRailwayStation", nullable = true)
	private float distanceFromRailwayStation;

	@Column(name = "closestBusStand", length = 255, nullable = true)
	@NotBlank
	@Size(max = 255)
	private String closestBusStand;
	
	@Column(name = "distanceFromBusStand", nullable = true)
	private float distanceFromBusStand;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="city_attachments", joinColumns=@JoinColumn(name="city_id",unique=false), inverseJoinColumns=@JoinColumn(name="attachment_id"))
	private Collection<Attachment> attachments= new ArrayList<Attachment>();
	
	
	@Column(name="tags", length=255, nullable=true)
	@Size(max=255)
	private String tags;
	
	@Transient
	private List<String> tagList;

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
	
	public List<String> getTagList() {
		return tagList;
	}
	
	public String getTags() {
		return tags;
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
	
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
		String tags="";
		if(tagList!=null) {
			tags=tagList.stream().collect(Collectors.joining(","));
		}
		this.tags=tags;
	}
	
	public void setTags(String tags) {
		this.tags = tags;
		if(tags!=null) {
			this.tagList = Arrays.asList(tags.split(","));
		}
	}
	
	public String getClosestAirport() {
		return closestAirport;
	}

	public void setClosestAirport(String closestAirport) {
		this.closestAirport = closestAirport;
	}

	public float getDistanceFromAirport() {
		return distanceFromAirport;
	}

	public void setDistanceFromAirport(float distanceFromAirport) {
		this.distanceFromAirport = distanceFromAirport;
	}

	public String getClosestRailwayStation() {
		return closestRailwayStation;
	}

	public void setClosestRailwayStation(String closestRailwayStation) {
		this.closestRailwayStation = closestRailwayStation;
	}

	public float getDistanceFromRailwayStation() {
		return distanceFromRailwayStation;
	}

	public void setDistanceFromRailwayStation(float distanceFromRailwayStation) {
		this.distanceFromRailwayStation = distanceFromRailwayStation;
	}

	public String getClosestBusStand() {
		return closestBusStand;
	}

	public void setClosestBusStand(String closestBusStand) {
		this.closestBusStand = closestBusStand;
	}

	public float getDistanceFromBusStand() {
		return distanceFromBusStand;
	}

	public void setDistanceFromBusStand(float distanceFromBusStand) {
		this.distanceFromBusStand = distanceFromBusStand;
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
