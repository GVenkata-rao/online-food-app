package com.poc.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class IngredientCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	@JsonIgnore
	@ManyToOne
	private Restaurant restaurant;

	@OneToMany(mappedBy = "category")
	private List<IngredientsItem> ingredientsItems;
}
