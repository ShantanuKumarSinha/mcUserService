package dev.shann.mcuserservice.controller;

import dev.shann.mcuserservice.dto1.AuthenticateUserDTO;
import dev.shann.mcuserservice.dto1.CreateUserDTO;
import dev.shann.mcuserservice.model.Users;
import dev.shann.mcuserservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody AuthenticateUserDTO authenticateUserDTO){
        var userFound = userService.authenticate(authenticateUserDTO) != null;
        return new ResponseEntity<>(userFound,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Users> createUser(@RequestBody CreateUserDTO createUserDTO){
        var user = userService.createUser(createUserDTO);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
}
