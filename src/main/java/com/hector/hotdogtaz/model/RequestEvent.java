package com.hector.hotdogtaz.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "request_events")
public class RequestEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private Request.Status previousStatus;


    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private Request.Status newStatus;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name ="request_id", nullable = false)
    private Request request;

    protected RequestEvent() {}

    public RequestEvent(Request.Status previousStatus, Request.Status newStatus, Request request) {
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.request = request;
    }

    @PrePersist
    protected void onCreate() {
        this.eventDate = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request.Status getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(Request.Status previousStatus) {
        this.previousStatus = previousStatus;
    }

    public Request.Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Request.Status newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
