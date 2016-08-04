package com.theironyard.services;

import com.theironyard.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nigel on 8/4/16.
 */
public interface LocationRepository extends JpaRepository<Location, Integer>{
}
