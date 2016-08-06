package com.theironyard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.theironyard.utilities.LocalDateTimeConverter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Nigel on 8/3/16.
 */
@Entity
@Table(name = "postings")
public class Posting {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int minSalary;

    @Column(nullable = false)
    private int maxSalary;

    @ManyToOne//(cascade = {CascadeType.ALL})
    private Location location;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column
    private LocalDateTime created_at = LocalDateTime.now();

    @ManyToMany(mappedBy = "postings")
    @JsonIgnore
    private Collection<User> applicants;

    public Posting() {
    }

    public Posting(String title, String description, int minSalary, int maxSalary, Location location) {
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public long getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Collection<User> getApplicants() {
        return applicants;
    }

    public void setApplicants(Collection<User> applicants) {
        this.applicants = applicants;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void addApplicant(User user){
        applicants.add(user);
    }
}
