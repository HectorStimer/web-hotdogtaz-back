package com.hector.hotdogtaz.model;


import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name= "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String password;

    private boolean active = true;

    @Column(nullable = false)
    private LocalDate createdAt;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;


    protected User(){}

    public User(String name, String email, String password, boolean active,  UserType type){
        this.name=name;
        this.email = email;
        this.password = password;
        this.active = active;
        this.type = type;
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now(); // metodo p qnd criar o usuario tacar a data insta
    }


    public enum UserType{
        ADMIN,
        CLERK
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name=name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }


    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
