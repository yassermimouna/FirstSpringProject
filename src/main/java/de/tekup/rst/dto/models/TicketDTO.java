package de.tekup.rst.dto.models;

import java.util.List;

import lombok.Data;

@Data
public class TicketDTO {
	
	private Integer clientId;
	
	private Integer tableNumero;
	
	private List<Integer> metsIds;
	
	private int nbCoverts;

}