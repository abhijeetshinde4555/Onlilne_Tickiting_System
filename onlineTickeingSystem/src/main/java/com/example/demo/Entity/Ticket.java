package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Integer ticketId;

    @ManyToOne
	@JoinColumn(name = "user_id")
    private User user;
    
    private String busNumber;
    
    @ManyToOne
	@JoinColumn(name = "route_id")
    private Route route;
    
    private String startStop;
    
    private String endStop;
    
    private Float fare;
    
    private Timestamp timestamp;


}
