package com.travel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.sql.Delete;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "attractions")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Attraction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5097374050321111892L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", unique = true, length = 80, nullable = false)
	@NotBlank
	@Size(max = 80)
	private String name;

	@Column(name = "aboutText", nullable = false)
	@NotBlank
	@Size(max=500)
	@Lob
	private String aboutText;
	
	@Column(name="phoneNumber")
	private String phoneNumber;
	
	@Column(name="hasEntryTicket")
	private boolean hasEntryTicket;
	
	@Column(name="timeRequired")
	private Integer timeRequired;
	
	@Column(name="ticketPrice")
	private Integer ticketPrice;
	
	@Column(name="additionalCommentsOnTicket")
	private String additionalCommentsOnTicket;
	
	@Column(name="prefferedStartTiming") 
	private String prefferedStartTiming;
	
	@Column(name="prefferedEndTiming")
	private String prefferedEndTiming;
	
	@Column(name="address")
	private String address;
	
	@Column(name="address2")
	private String address2;
	
	@Column(name="postalCode")
	private String postalCode;
	
	@Column(name="country")
	private String country;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", referencedColumnName = "id")
	private City city;
	
	@Column(name="state")
	private String state;
	
	@Column(name = "popularity", nullable = false)
	private Integer popularity;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="attraction_attachments", joinColumns=@JoinColumn(name="attraction_id",unique=false), inverseJoinColumns=@JoinColumn(name="attachment_id"))
	private Collection<Attachment> attachments= new ArrayList<Attachment>();
	
	@Transient
	private List<String> tagList;
	
	@Transient
	private List<String> mdtTagList;
	
	@Column(name="tags", length=255, nullable=true)
	@Size(max=255)
	@JsonIgnore
	private String tags;
	
	@Column(name="mdtTags", length=255, nullable=true)
	@Size(max=255)
	@JsonIgnore
	private String mdtTags;
	
	
	public List<String> getTagList() {
		if(tagList==null) {
			tagList=new ArrayList<String>();
		}
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
		String tags = "";
		if (tagList != null) {
			tags = tagList.stream().collect(
					Collectors.joining(","));
		}
		this.tags = tags;

	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
		if(tags!=null) {
			this.tagList=Arrays.asList(tags.split(","));
		}
	}
	
	public List<String> getMdtTagList() {
		if(mdtTagList==null) {
			mdtTagList=new ArrayList<String>();
		}
		return mdtTagList;
	}

	public void setMdtTagList(List<String> mdtTagList) {
		this.mdtTagList = mdtTagList;
		String mdtTags = "";
		if (mdtTagList != null) {
			mdtTags = mdtTagList.stream().collect(
					Collectors.joining(","));
		}
		this.mdtTags = mdtTags;

	}

	public String getMdtTags() {
		return mdtTags;
	}

	public void setMdtTags(String mdtTags) {
		this.mdtTags = mdtTags;
		if(mdtTags!=null) {
			this.mdtTagList=Arrays.asList(mdtTags.split(","));
		}
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAboutText() {
		return aboutText;
	}

	public void setAboutText(String aboutText) {
		this.aboutText = aboutText;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isHasEntryTicket() {
		return hasEntryTicket;
	}

	public void setHasEntryTicket(boolean hasEntryTicket) {
		this.hasEntryTicket = hasEntryTicket;
	}

	public Integer getTimeRequired() {
		return timeRequired;
	}

	public void setTimeRequired(Integer timeRequired) {
		this.timeRequired = timeRequired;
	}

	public String getPrefferedStartTiming() {
		return prefferedStartTiming;
	}

	public void setPrefferedStartTiming(String prefferedStartTiming) {
		this.prefferedStartTiming = prefferedStartTiming;
	}

	public String getPrefferedEndTiming() {
		return prefferedEndTiming;
	}

	public void setPrefferedEndTiming(String prefferedEndTiming) {
		this.prefferedEndTiming = prefferedEndTiming;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getPopularity() {
		return popularity;
	}

	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}

	public Integer getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getAdditionalCommentsOnTicket() {
		return additionalCommentsOnTicket;
	}

	public void setAdditionalCommentsOnTicket(String additionalCommentsOnTicket) {
		this.additionalCommentsOnTicket = additionalCommentsOnTicket;
	}
	
}
