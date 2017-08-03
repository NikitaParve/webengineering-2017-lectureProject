package de.unikassel.webengineering.project.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Luan Hajzeraj on 29.06.2017.
 */
@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public void addUser(@RequestBody User newUser){
        userService.addUser(newUser);

        /*
        LOG.info("Add user: ID={}, email={}, password={}, text={}",
                newUser.getId(), newUser.getEmail(), newUser.getPassword(),newUser.getUserText());
        */
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public List<User> getUserList(){
        LOG.info("Return the List of all persistent users");
        return userService.getUserList();
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public User getUserByEmailAndPassword(@PathVariable Long id){
        LOG.info("Return the User with ID={}", id);
        return userService.getUserByID(id);
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.DELETE)
    public void deleteUserByID(@PathVariable Long id){
        LOG.info("Delete USer with ID={}", id);
        userService.deleteUSerByID(id);
    }

    @RequestMapping(value = "/api/user/nextUnread", method = RequestMethod.GET)
    public User getNextUnreadUser(){
        LOG.info("Get a User, that not read text");

        User user = userService.getNextUnreadUser();

        return user;
    }

    @RequestMapping(value = "/api/user/like", method = RequestMethod.POST)
    public void likeTextOfUserWithID(@RequestBody User user){

        userService.likeTextOfUserWithID(user.getId());
    }
}
