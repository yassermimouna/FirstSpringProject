package de.tekup.rst.services;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import de.tekup.rst.dto.models.TicketDTO;
import de.tekup.rst.entities.Client;
import de.tekup.rst.entities.MetEntity;
import de.tekup.rst.entities.TableEntity;
import de.tekup.rst.entities.TicketEntity;
import de.tekup.rst.repositories.ClientRepository;
import de.tekup.rst.repositories.MetRepository;
import de.tekup.rst.repositories.TableRepository;
import de.tekup.rst.repositories.TicketRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {
	
	private ClientRepository clientRepository;
	private TableRepository tableRepository;
	private MetRepository metRepository;
	private TicketRepository ticketRepository;
	
	public double createTicket(TicketDTO ticketDTO) {
		
		Client client = clientRepository
				.findById(ticketDTO.getClientId()).get();
		TableEntity tableEntity = tableRepository
				.findById(ticketDTO.getTableNumero()).get();
		
		List<MetEntity> mets = metRepository
				.findAllById(ticketDTO.getMetsIds());
		
		double addition = mets.stream()
							.mapToDouble(met -> met.getPrix())
							.sum() + tableEntity.getSupplement();
		TicketEntity ticketEntity = new TicketEntity();
		
		ticketEntity.setClient(client);
		ticketEntity.setTable(tableEntity);
		ticketEntity.setMets(mets);
		ticketEntity.setNbCouverts(ticketDTO.getNbCoverts());
		ticketEntity.setAddition(addition);
		ticketEntity.setDateTime(LocalDateTime.now());
		
		ticketRepository.save(ticketEntity);
		return addition;
	}

}