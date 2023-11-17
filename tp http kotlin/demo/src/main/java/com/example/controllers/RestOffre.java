package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Offre;
import com.example.repo.OffreRepo;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offre")
public class RestOffre {
	@Autowired
	OffreRepo offreRepo;
	
	@GetMapping
	public List<Offre> GetAll(){
		return offreRepo.findAll();
	}
	@GetMapping("/{id}") 
	public Offre GetById(@PathVariable Integer id) {
		return offreRepo.findById(id).get();
	}
	@PostMapping
	public Offre saveOffre(@RequestBody Offre offre) {
		return offreRepo.save(offre);	
	}
	@DeleteMapping("/{id}")
	public Boolean DeleteOffre(@PathVariable Integer id) {
		offreRepo.deleteById(id);
		return true;
	}
	@PutMapping("/{id}")
	public Offre updateOffre(@PathVariable Integer id,@RequestBody Offre newOffre) {
		newOffre.setCode(id);
		return offreRepo.save(newOffre);	
	}
	
}
