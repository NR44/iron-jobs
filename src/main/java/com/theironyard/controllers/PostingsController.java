package com.theironyard.controllers;

import com.theironyard.command.PostingCommand;
import com.theironyard.entities.Posting;
import com.theironyard.entities.User;
import com.theironyard.exceptions.TokenExpiredException;
import com.theironyard.services.LocationRepository;
import com.theironyard.services.PostingRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Nigel on 8/3/16.
 */
@CrossOrigin
@RestController
public class PostingsController {

    @Autowired
    UserRepository users;

    @Autowired
    PostingRepository postings;

    @Autowired
    LocationRepository locations;

    @RequestMapping(path = "/postings", method = RequestMethod.GET)
    public List<Posting> getAllPostings(){

        return postings.findAll();
    }

    @RequestMapping(path = "/postings", method = RequestMethod.POST)
    public Posting addPosting(@RequestBody Posting posting, @RequestHeader(value = "Authorization") String auth){

        User user = getUserFromAuth(auth);

        posting.setOwner(user);

        postings.save(posting);

        return posting;
    }

    public User getUserFromAuth(String auth) {
        User user = users.findFirstByToken(auth.split(" ")[1]);
        if (!user.isTokenValid()) {
            throw new TokenExpiredException();
        }
        return user;
    }
}