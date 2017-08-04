package com.mathemagica.api.controller.user;

import com.mathemagica.model.user.User;
import com.mathemagica.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by Azael on 2017/08/03.
 */
@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAll();
        if(users != null && users.size() > 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/{email}", method = RequestMethod.GET,
            produces =  {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getUser(@PathVariable("email") String email){
        User user = userService.getByEmail(email);
        if(user != null){
            System.out.println("User with email " + email + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = {"/api/user/{name}","/api/user/{surname}"}, method = RequestMethod.GET,
            produces =  {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getUser(@PathVariable("name") String name,@PathVariable("surname") String surname){
        User user = userService.getByNameAndSurname(name,surname);
        if(user != null){
            System.out.println("User with name " + name + " and surname " + surname + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){

        System.out.println("Creating User " + user.getEmail());

        if(userService.isUserExist(user)){
            System.out.println("A User with email " + user.getEmail() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{email}").buildAndExpand(user.getEmail()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/user/{email}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("email") String email, @RequestBody User user){
        System.out.println("Updating User " + email);

        User currentUser = userService.getByEmail(email);

        if (currentUser==null) {
            System.out.println("User with email " + email + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        currentUser.setPassword(user.getPassword());
        currentUser.setActive(user.isActive());
        userService.update(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/{email}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("email") String email) {
        System.out.println("Fetching & Deleting User with email " + email);

        User user = userService.getByEmail(email);
        if (user == null) {
            System.out.println("Unable to delete. User with email " + email + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
