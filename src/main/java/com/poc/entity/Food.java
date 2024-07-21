package com.poc.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String description;
	private Long price;

	@ManyToOne
	private Category foodCategory;
	@Column(length = 1000)
	@ElementCollection
	private List<String> images;
	private boolean available;

	@ManyToOne
	private Restaurant restaurant;

	private boolean isVegetarian;
	private boolean isSeasonal;
	@ManyToMany
	private List<IngredientsItem> ingredientsItems;

	private Date creationDate;
}
