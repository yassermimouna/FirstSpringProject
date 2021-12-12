package de.tekup.rst.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import de.tekup.rst.dto.models.ClientResDTO;
import de.tekup.rst.dto.models.MetDTO;
import de.tekup.rst.dto.models.TableDTO;
import de.tekup.rst.entities.Client;
import de.tekup.rst.entities.MetEntity;
import de.tekup.rst.entities.Plat;
import de.tekup.rst.entities.TableEntity;
import de.tekup.rst.entities.TicketEntity;
import de.tekup.rst.repositories.ClientRepository;
import de.tekup.rst.repositories.MetRepository;
import de.tekup.rst.repositories.TableRepository;
import de.tekup.rst.repositories.TicketRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatService {
	
	private MetRepository metRepository;
	private ClientRepository clientRepository;
	private TableRepository tableRepository;
	private ModelMapper  mapper;
	private TicketRepository ticketRepository;

	public MetDTO platPlusAchetee(LocalDate debut, LocalDate fin) {
		List<MetEntity> metEntities = metRepository.findAll();
		metEntities.removeIf(met -> !(met instanceof Plat));
		
		for (MetEntity metEntity : metEntities) {
			metEntity.getTickets()
			.removeIf(t-> !(t.getDateTime().toLocalDate().isAfter(debut) && 
					t.getDateTime().toLocalDate().isBefore(fin)));
		}
		
		int max = -1;
		MetEntity entity = null;
		
		for (MetEntity metEntity : metEntities) {
			if(metEntity.getTickets().size()>max) {
				max = metEntity.getTickets().size();
				entity  = metEntity;
			}
		}
		MetDTO dto = mapper.map(entity, MetDTO.class);
		dto.setType("Plat");
		return dto;
	}
	
	public ClientResDTO fidele() {
		List<Client> clients = clientRepository.findAll();
		
		int max = -1;
		Client clientFidele = null;
		
		for (Client client : clients) {
			if(client.getTickets().size()> max) {
				max = client.getTickets().size();
				clientFidele = client;
			}
		}
		
		return mapper.map(clientFidele, ClientResDTO.class);
	}
	
	
	public TableDTO plusReservee() {
		
		TableEntity table = tableRepository.findAll().stream()
								.max(Comparator.comparing(t->t.getTickets().size()))
								.get();
		
		return mapper.map(table, TableDTO.class);
	}

	public String clientJour(int id) {
		Client client = clientRepository.findById(id).get();
		List<TicketEntity> tickets = client.getTickets();
		List<DayOfWeek> days = tickets.stream()
							.map(t-> t.getDateTime().getDayOfWeek())
							.collect(Collectors.toList());
		System.out.println(days);
		
		Map<DayOfWeek, Long> map = days.stream()
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(map);
		DayOfWeek day = map.entrySet().stream()
					.max(Map.Entry.comparingByValue())
					.get().getKey();
		
		return day.getDisplayName(TextStyle.FULL, new Locale("fr"));
	}
	
	public Map<String, Double> revenu() {
		List<TicketEntity> tickets= ticketRepository.findAll();
		LocalDate today= LocalDate.now();
		
		double todayAddition = tickets.stream()
				.filter(t->t.getDateTime().toLocalDate().isEqual(today))
				.mapToDouble(t->t.getAddition())
				.sum();
		
		double monthAddition =  tickets.stream()
				.filter(t->t.getDateTime().getMonthValue()==today.getMonthValue())
				.mapToDouble(t->t.getAddition())
				.sum();
		
		TemporalField weekOfYear = WeekFields.ISO.weekOfWeekBasedYear();
		double weekAddition =  tickets.stream()
				.filter(t->t.getDateTime().get(weekOfYear)==today.get(weekOfYear))
				.mapToDouble(t->t.getAddition())
				.sum();
		
		Map<String, Double> map = new HashMap<>();
		map.put("Jour", todayAddition);
		map.put("Week", weekAddition);
		map.put("Month", monthAddition);
		
		return map;
	
	}
}