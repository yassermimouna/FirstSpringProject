package de.tekup.rst.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
