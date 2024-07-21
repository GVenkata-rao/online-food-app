package com.poc.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private User owner;

	private String name;
	private String description;
	private String cuisineType;

	@OneToOne
	private Address address;
	@Embedded
	private ContactInformation contactInfo;

	private String openingHours;
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<Order> orders;
	@ElementCollection
	@Column(length = 1000)
	private List<String> images;

	private LocalDateTime registrationmDate;

	private boolean open;
//	private boolean close;
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<Food> foods;
}