package com.theironyard.controllers;

import com.theironyard.command.PostingCommand;
import com.theironyard.entities.Location;
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

    /**
     * Shows all postings
     * @return all postings
     */
    @RequestMapping(path = "/postings", method = RequestMethod.GET)
    public List<Posting> getAllPostings(){
        return postings.findAll();
    }

    /**
     * Shows all postings in reverse order
     * @return list of postings in reverse order
     */
    @RequestMapping(path = "/postings/reverse", method = RequestMethod.GET)
    public List<Posting> getAllPostingsReverse(){
        return postings.findAllByOrderByIdDesc();
    }

    /**
     * Makes a list of postings filtered by a salary and location Id.
     * @param salary
     * @param id
     * @return List of postings.
     */
    @RequestMapping(path = "/postings/{salary}/{id}", method = RequestMethod.GET)
    public List<Posting> getPostingsBySalaryAndLocation(@PathVariable int salary, @PathVariable int id){
        Location location = locations.findOne(id);
        return postings.findAllByMinSalaryGreaterThanEqualAndLocationContaining(salary, location);
    }

    /**
     * Makes a list of postings filtered by salary
     * @param salary
     * @return List of postings
     */
    @RequestMapping(path = "/postings/salary/{salary}", method = RequestMethod.GET)
    public List<Posting> getPostingsBySalary(@PathVariable int salary){
        return postings.findAllByMinSalaryGreaterThanEqual(salary);
    }


    /**
     * Create a new posting
     * @param posting
     * @param auth
     * @return get that posting detail
     */
    @RequestMapping(path = "/postings", method = RequestMethod.POST)
    public Posting addPosting(@RequestBody Posting posting, @RequestHeader(value = "Authorization") String auth){

        User user = getUserFromAuth(auth);

        posting.setOwner(user);
        Location tempLocation = locations.findFirstByCityAndState(posting.getLocation().getCity(), posting.getLocation().getState());

        if (tempLocation == null) {
            locations.save(tempLocation);
        }
        posting.setLocation(tempLocation);
        postings.save(posting);

        return posting;
    }

    /**
     * Get the detail of a particular posting, by id.
     * @param id
     * @param auth
     * @return the posting requested
     */
    @RequestMapping(path = "/postings/{id}", method = RequestMethod.GET)
    public Posting getPosting(@PathVariable int id, @RequestHeader(value = "Authorization") String auth){
        return postings.findOne(id);
    }

    /**
     * Get user by the token.
     * @param auth
     * @return returns the user.
     */
    public User getUserFromAuth(String auth) {
        User user = users.findFirstByToken(auth.split(" ")[1]);
        if (!user.isTokenValid()) {
            throw new TokenExpiredException();
        }
        return user;
    }
}