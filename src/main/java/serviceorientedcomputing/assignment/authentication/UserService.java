/*
 * UserService.java -- Defines UserService class 
 * This code is implemented as part of assignment   
 * course Service Oriented Computing of MTECH Program Software Engineering
 * Student Name : Deepish Sharma
 * Student Id   : 2022MT93012
 * Course       : Scalable Services
 * Program      : MTECH Software Engineering
 * Student Email: 2022MT93012@wilp.bits-pilani.ac.in
 */
package serviceorientedcomputing.assignment.authentication;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The <code>UserService</code> is microservice that provides REST/JSON 
 * interface to manage Users. 
 * It provides functionality to create, retrieve, update and delete users
 * @author Deepish Sharma
 */
@Slf4j
@RestController
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Retrieve User with given id
     * @param id             : Customer id of type Long
     * @return               : Customer of type CustomerEntity  
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        log.debug("In getUser()... id = " + id);
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            return ResponseEntity.ok(optionalEntity.get());
        } else {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("User with ID " + id + " not found");
        }
    }
    
    /**
     * Retrieves list of all Users
     * @return              : list of type List<UserEntity>
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> listUsers() {
        log.debug("In listUsers()...");
        List<UserEntity> customerList = userRepository.findAll();
        return ResponseEntity.ok(customerList);
    }
    
    /**
     * Creates new user
     * @param entity         : entity of type UserEntity
     * @return               : customer of type UserEntity  
     */
    @Transactional
    @PostMapping("/users")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity entity) {
        log.debug("In saveUser()...");
        userRepository.save(entity);
        return ResponseEntity.ok(entity);
    } 
    
    /**
     * Updates existing user with specified id
     * @param id             : user id of type Long
     * @param entity         : entity of type UserEntity
     * @return               : user of type UserEntity  
     */
    @Transactional
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, 
        @RequestBody UserEntity entity) {
        log.debug("In updateUser()... id = " + id);
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            UserEntity databaseEntity = optionalEntity.get();
            databaseEntity.setFirstName(entity.getFirstName());
            databaseEntity.setLastName(entity.getLastName());
            databaseEntity.setEmail(entity.getEmail());
            databaseEntity.setUsername(entity.getUsername());
            databaseEntity.setPassword(entity.getPassword());
            databaseEntity.setRole(entity.getRole());
            databaseEntity.setEnabled(entity.isEnabled());
            return ResponseEntity.ok(optionalEntity.get());
        } else {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("User with ID " + id + " not found");
        }
    }
    
    /**
     * Deletes existing user with specified id
     * @param id             : user id of type Long
     * @return               : user id of type Long
     */
    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        log.debug("In deleteUser()... id = " + id);
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        
        if (optionalEntity.isPresent()) {
            userRepository.delete(optionalEntity.get());
            return ResponseEntity.status(HttpStatus.OK)
                .body("User with ID " + id + " deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User with ID " + id + " not found");
        }
    }  
}
