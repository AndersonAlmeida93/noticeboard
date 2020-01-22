package com.andersonalmeida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andersonalmeida.models.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{
	

}
