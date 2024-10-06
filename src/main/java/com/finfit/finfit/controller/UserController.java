package com.finfit.finfit.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.finfit.finfit.model.User;
import com.finfit.finfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try{
            boolean isUserCreated = userService.createUser(user);
            if (!isUserCreated) {
                return new ResponseEntity<String>("User Creation Failed", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>("User Created Successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("User Creation Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
            String token=userService.verify(user);
            if(token!=null) {
                return new ResponseEntity<String>(token, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("Please check your credentials", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Login Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/details")
    public ResponseEntity<String> details() {
        try{
            UserDetails user=userService.getUserDetails();
            System.out.println(user.getUsername());
            if(user!=null) {
                return new ResponseEntity<String>("username : "+user.getUsername()+"\n"+"password : "+ user.getPassword()+"\n"+user.getAuthorities().toString(), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("Unable to get user details", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Unable to get user details", HttpStatus.BAD_REQUEST);
        }
    }
}
