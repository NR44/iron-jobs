package com.theironyard.controllers;

import com.theironyard.services.PostingRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

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


}
