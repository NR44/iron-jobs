package com.theironyard.services;

import com.theironyard.entities.Location;
import com.theironyard.entities.Posting;
import com.theironyard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nigel on 8/3/16.
 */
public interface PostingRepository extends JpaRepository<Posting, Integer>{
    List<Posting> findAllByOrderByIdDesc();
    List<Posting> findAllByMinSalaryGreaterThanEqual(int minSalary);
    List<Posting> findAllByMinSalaryGreaterThanEqualAndLocationContaining(int minSalary, Location location);
}
