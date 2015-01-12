package com.travel.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
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

	@Column(name = "aboutText", length = 255, nullable = false)
	@NotBlank
	@Size(max = 255)
	private String aboutText;
	
	@Column(name="phoneNumber")
	private String phoneNumber;
	
	@Column(name="hasEntryTicket")
	private boolean hasEntryTicket;
	
	@Column(name="timeRequired")
	private String timeRequired;
	
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
	
	/*@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", referencedColumnName = "id")*/
	@JsonIgnore
	private City city;
	
	@Column(name="state")
	private String state;
	
	@Column(name = "popularity", nullable = false)
	private Integer popularity;
}
