package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	public Tag findByDisplayText(final String displayText);

}