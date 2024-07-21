package com.poc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class IngredientsItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@JsonIgnore
	@ManyToOne
	private IngredientCategory category;

	@JsonIgnore
	@ManyToOne
	private Restaurant restaurant;

	private boolean instock= true;
}
