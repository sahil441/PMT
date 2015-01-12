package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.model.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {

}
