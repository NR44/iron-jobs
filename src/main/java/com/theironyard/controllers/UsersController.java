package com.theironyard.controllers;

import com.theironyard.command.UserCommand;
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

import java.util.HashMap;
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

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id){
        User user = users.findOne(id);

        return user;
    }

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

    @RequestMapping(path = "/token", method = RequestMethod.POST)
    public Map getToken(@RequestBody UserCommand command) throws Exception {
        User user = checkLogin(command);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", user.getToken());
        return tokenMap;
    }

    @RequestMapping(path = "/token/regenerate", method = RequestMethod.PUT)
    public Map regenerateToken(@RequestBody UserCommand command) throws Exception{
        User user = checkLogin(command);
        String token = user.regenerate();
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return tokenMap;
    }

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
}
