package com.theironyard.services;

import com.theironyard.entities.Posting;
import com.theironyard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nigel on 8/3/16.
 */
public interface PostingRepository extends JpaRepository<Posting, Integer>{

}
