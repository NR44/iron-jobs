package com.theironyard.controllers;

import com.theironyard.command.UserCommand;
import com.theironyard.entities.Posting;
import com.theironyard.entities.User;
import com.theironyard.exceptions.LoginFailedException;
import com.theironyard.exceptions.TokenExpiredException;
import com.theironyard.exceptions.UserNotFoundException;
import com.theironyard.services.LocationRepository;
import com.theironyard.services.PostingRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nigel on 8/3/16.
 */
@CrossOrigin
@RestController
public class UsersController {

    @Autowired
    UserRepository users;

    @Autowired
    PostingRepository postings;

    @Autowired
    LocationRepository locations;

    /**
     * Get user's info.
     * @param id
     * @return the details of user of a particular user.
     */
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id){
        User user = users.findOne(id);

        return user;
    }

    /**
     * Allows a user to login
     * @param command
     * @return returns the users detail after they've logged in.
     * @throws Exception
     */
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public User login(@RequestBody UserCommand command) throws Exception {
        User user = users.findFirstByName(command.getUsername());

        if (user == null) {
            user = new User(command.getUsername(), PasswordStorage.createHash(command.getPassword()));
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(command.getPassword(), user.getPassword())) {
            throw new LoginFailedException();
        }

        return user;
    }

    /**
     * Allows user to apply to a posting. Adds that posting to the list of postings they've applied for.
     * @param id
     * @param auth
     * @return returns the posting added to the user's list.
     */
    @RequestMapping(path = "/users/postings/{id}", method = RequestMethod.POST)
    public Posting applyToPosting(@PathVariable int id, @RequestHeader(value = "Authorization") String auth){
        User user = getUserFromAuth(auth);
        Posting newPosting = postings.findOne(id);

        user.addPosting(newPosting);
        newPosting.addApplicant(user);

        users.save(user);
        postings.save(newPosting);

        return newPosting;
    }

    /**
     * Get jobs applied for
     * @param auth
     * @return Collection of all postings the user has applied for
     */
    @RequestMapping(path = "/users/postings", method = RequestMethod.GET)
    public Collection<Posting> postingsAppliedFor(@RequestHeader(value = "Authorization") String auth){
        User user = getUserFromAuth(auth);
        return user.getPostings();
    }

    /**
     * Gets the token of the user that is currently logged in.
     * @param command
     * @return token of the current user
     * @throws Exception
     */
    @RequestMapping(path = "/token", method = RequestMethod.POST)
    public Map getToken(@RequestBody UserCommand command) throws Exception {
        User user = checkLogin(command);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", user.getToken());
        return tokenMap;
    }

    /**
     * Regenerate a token once it has expired.
     * @param command
     * @return new token
     * @throws Exception
     */
    @RequestMapping(path = "/token/regenerate", method = RequestMethod.PUT)
    public Map regenerateToken(@RequestBody UserCommand command) throws Exception{
        User user = checkLogin(command);
        String token = user.regenerate();
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return tokenMap;
    }

    /**
     * Checks if user is logged in
     * @param command
     * @return user, found by username
     * @throws Exception
     */
    public User checkLogin(UserCommand command) throws Exception{
        User user = users.findFirstByName(command.getUsername());
        if(user == null){
            throw new UserNotFoundException();
        }

        if(!PasswordStorage.verifyPassword(command.getPassword(), user.getPassword())){
            throw new LoginFailedException();
        }

        if(!user.isTokenValid()){
            throw new TokenExpiredException();
        }

        return user;
    }

    public User getUserFromAuth(String auth) {
        User user = users.findFirstByToken(auth.split(" ")[1]);
        if (!user.isTokenValid()) {
            throw new TokenExpiredException();
        }
        return user;
    }
}
