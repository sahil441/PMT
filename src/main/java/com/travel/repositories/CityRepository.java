package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.model.City;

public interface CityRepository extends JpaRepository<City, Integer> {

	public City findByName(final String name);
}