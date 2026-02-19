package com.hector.hotdogtaz.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "request_events")
public class RequestEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status tpStatusPrevious;
    private Status tpStatusNew;
    private LocalDateTime eventDate;


    @ManyToOne
    @JoinColumn(name ="request_id")
    private Request request;






    public enum Status{
        CREATED,
        IN_PREPARATION,
        READY,
        COMPLETED,
        CANCELED
    }




}
