package de.tekup.rst.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class TableEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero ;
    
    private int nbCouverts;
    
    @Enumerated(EnumType.STRING)
    private TableType type;
    
    private double supplement;
    
    @OneToMany(mappedBy = "table")
	List<TicketEntity> tickets;
}
