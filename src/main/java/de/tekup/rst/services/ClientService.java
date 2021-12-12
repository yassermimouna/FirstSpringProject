package de.tekup.rst.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import de.tekup.rst.dto.models.ClientReqDTO;
import de.tekup.rst.dto.models.ClientResDTO;
import de.tekup.rst.entities.Client;
import de.tekup.rst.repositories.ClientRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService {

	
	private ClientRepository clientRepository;
	private ModelMapper mapper;
	public ClientResDTO saveToDB(ClientReqDTO clientReqDTO) {
		/* Client client = new Client();
		client.setName(clientDto.getName());
		client.setPrenom(clientDto.getPrenom());
		client.setDateDeNaissance(clientDto.getDateDeNaissance());
		client.setEmail(clientDto.getEmail());
		client.setPhone(clientDto.getPhone());
		
		return clientRepository.save(client);  */
		
		Client entity = mapper.map(clientReqDTO, Client.class);
		clientRepository.save(entity);
		ClientResDTO clientResDTO = mapper.map(entity, ClientResDTO.class);
		return clientResDTO;
	}
}
