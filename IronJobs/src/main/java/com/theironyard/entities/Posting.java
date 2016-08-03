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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Posting {

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private long minSalary;

    @Column(nullable = false)
    private long maxSalary;

    @Column(nullable = false)
    private Location location;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column
    private LocalDateTime created_at = LocalDateTime.now();

    @ManyToMany(mappedBy = "postings")
    @JsonIgnore
    private Collection<User> applicants;
}
