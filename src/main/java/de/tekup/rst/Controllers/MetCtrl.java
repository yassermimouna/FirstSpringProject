package de.tekup.rst.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.tekup.rst.dto.models.MetDTO;
import de.tekup.rst.services.MetService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MetCtrl {
	
	private MetService metService;
	
	@PostMapping("/api/mets")
	public MetDTO saveMet(@RequestBody MetDTO metDTO) {
		return metService.saveMetInDB(metDTO);
	}
	
	@GetMapping("/api/mets")
	public List<MetDTO> getMets() {
		return metService.getAllMets();
	}

}