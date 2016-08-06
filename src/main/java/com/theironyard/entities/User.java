package com.theironyard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.theironyard.utilities.LocalDateTimeConverter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

/**
 * Created by Nigel on 8/3/16.
 */
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    public static final int TOKEN_EXPIRATION = 30;

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column
    @JsonIgnore
    private String email;

    @ManyToOne
    private Location location;

    @ManyToMany
    private Collection<Posting> postings;

    @Column(nullable = false, unique = true)
    @ColumnDefault("'NNNNNNNNNNNNNN'")
    @JsonIgnore
    private String token;

    @Column(nullable = false)
    @ColumnDefault("'1944-04-04'")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime expiration;

    public User() {
        setTokenAndExpiration();
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        setTokenAndExpiration();
    }

    private void setTokenAndExpiration(){
        token = generateToken();
        expiration = LocalDateTime.now().plus(TOKEN_EXPIRATION, ChronoUnit.DAYS);
    }

    public String generateToken(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTokenValid(){
        return expiration.isAfter(LocalDateTime.now());
    }

    public String regenerate(){
        setTokenAndExpiration();
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Collection<Posting> getPostings() {
        return postings;
    }

    public void setPostings(Collection<Posting> postings) {
        this.postings = postings;
    }

    public void addPosting(Posting posting){
        postings.add(posting);
    }
}