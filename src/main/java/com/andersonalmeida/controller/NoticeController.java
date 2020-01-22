package com.andersonalmeida.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andersonalmeida.models.Notice;
import com.andersonalmeida.repository.NoticeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class NoticeController {

	@Autowired
	NoticeRepository noticeRepository;
	
	@GetMapping("/notices")
	public List<Notice> getAllNotices(){
		
		return noticeRepository.findAll();
	
	}
	
	@GetMapping("/notices{id}")
	public ResponseEntity<Notice> getNoticeById(@PathVariable(value = "id") Long noticeId)
		throws ResourceNotFoundException{
		
		Notice notice = noticeRepository.findById(noticeId)
				.orElseThrow(() -> new ResourceNotFoundException("Notice Not found for this id :: " + noticeId));
		
		return ResponseEntity.ok().body(notice);
	}
	
	@PostMapping("/notices")
	public Notice createNotice(@Valid @RequestBody Notice notice) {
		Calendar sysdate = new GregorianCalendar();
		notice.setPublishDate(sysdate.getTime());
		return noticeRepository.save(notice);
	}
	
	@PutMapping("/notices{id}")
	public ResponseEntity<Notice> updateNotice(@PathVariable(value = "id") Long noticeId, @Valid @RequestBody Notice noticeDetais) throws ResourceNotFoundException{
		
		Notice notice = noticeRepository.findById(noticeId)
				.orElseThrow(() -> new ResourceNotFoundException("Notice Not found for this id :: " + noticeId));
		
		notice.setTitle(noticeDetais.getTitle());
		notice.setDescription(noticeDetais.getDescription());
		notice.setPublishDate(noticeDetais.getPublishDate());
		notice.setVisualizationDate(noticeDetais.getVisualizationDate());
		
		final Notice updateNotice = noticeRepository.save(notice);
		
		return ResponseEntity.ok(updateNotice);
	}
	
	@DeleteMapping("/notice/{id}")
	public Map<String, Boolean> deleteNotice(@PathVariable(value = "id") Long noticeId) throws ResourceNotFoundException{
		
		Notice notice = noticeRepository.findById(noticeId)
				.orElseThrow(() -> new ResourceNotFoundException("Notice Not found for this id :: " + noticeId));
		
		noticeRepository.delete(notice);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted" , Boolean.TRUE);
		return response;
	}
	
}
