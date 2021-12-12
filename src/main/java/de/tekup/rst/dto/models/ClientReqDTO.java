package de.tekup.rst.dto.models;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClientReqDTO {
	
    private String name;
	
	private String prenom;
	
	private LocalDate DateDeNaissance;
	
	private String email;
	
	private String phone;
}
