package de.tekup.rst.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String prenom;
	
	private LocalDate DateDeNaissance;
	
	private String email;
	
	private String phone;
	@OneToMany(mappedBy = "client")
	private List<TicketEntity> tickets;
	
	public String getNomComplet() {
		return prenom + " " +name;
	}
	
	public int getAge() {
		return (int) ChronoUnit.YEARS
				.between(DateDeNaissance, LocalDate.now());
	}
}
