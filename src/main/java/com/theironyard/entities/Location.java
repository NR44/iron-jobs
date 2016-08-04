package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by Nigel on 8/3/16.
 */
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
