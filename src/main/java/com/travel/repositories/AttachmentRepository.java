package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.model.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer>{

}
