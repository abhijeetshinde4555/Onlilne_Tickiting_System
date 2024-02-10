package com.example.demo.Entity;


import java.util.Map;

import jakarta.persistence.*;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Route {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Integer routeId;
	
	private String routeName;
	private String description;
	
    @ElementCollection
    @CollectionTable(name = "your_map_table_name", joinColumns = @JoinColumn(name = "entity_id"))
    @MapKeyColumn(name = "map_key")
    @Column(name = "map_value")
	private Map<String , Float> stopsAndRates;
	
	
}
