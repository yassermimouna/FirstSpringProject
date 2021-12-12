package de.tekup.rst.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import de.tekup.rst.dto.models.MetDTO;
import de.tekup.rst.entities.Dessert;
import de.tekup.rst.entities.Entree;
import de.tekup.rst.entities.MetEntity;
import de.tekup.rst.entities.Plat;
import de.tekup.rst.repositories.MetRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MetService {
	
	private MetRepository metRepository;
	private ModelMapper mapper;
	
	public MetDTO saveMetInDB(MetDTO dto) {
		
		
		MetEntity metEntity =null;
		
		switch (dto.getType()) {
		case "Plat": metEntity = mapper.map(dto, Plat.class);
				break;
		case "Entree": metEntity = mapper.map(dto, Entree.class);
		break;
		case "Dessert": metEntity = mapper.map(dto,Dessert.class);
		break;

		}
		metEntity=metRepository.save(metEntity);
		dto.setId(metEntity.getId());
		return dto;
	}

	public List<MetDTO> getAllMets(){
		//List<MetEntity> mets = metRepository.findAll();
		
//		List<MetDTO> dtos= new ArrayList<>();
//		for (MetEntity metEntity : mets) {
//			MetDTO dto = mapper.map(metEntity, MetDTO.class);
//			dto.setType(metEntity.getClass().getSimpleName());
//			dtos.add(dto);
//			
//		}
		
	
		return metRepository.findAll().stream()
							.map(ent ->  {
								MetDTO dto = mapper.map(ent, MetDTO.class);
								dto.setType(ent.getClass().getSimpleName());
								return dto;
							}).collect(Collectors.toList());
	}
}