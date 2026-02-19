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
    private Status previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private Status newStatus;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name ="request_id", nullable = false)
    private Request request;

    protected RequestEvent() {}

    public RequestEvent(Status previousStatus, Status newStatus, Request request) {
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.request = request;
    }

    @PrePersist
    protected void onCreate() {
        this.eventDate = LocalDateTime.now();
    }

    public enum Status{
        CREATED,
        IN_PREPARATION,
        READY,
        COMPLETED,
        CANCELED
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(Status previousStatus) {
        this.previousStatus = previousStatus;
    }

    public Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Status newStatus) {
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
