package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offre {
	@Id
	private int code;
	private String intitulé;
	private String specialité;
	private String société;
	private int nbpostes;
	private String pays;	
}
