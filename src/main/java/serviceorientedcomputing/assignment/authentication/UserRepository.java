/*
 * UserRepository.java -- Defines UserRepository class 
 * This code is implemented as part of assignment   
 * course Service Oriented Computing of MTECH Program Software Engineering
 * Student Name : Deepish Sharma
 * Student Id   : 2022MT93012
 * Course       : Scalable Services
 * Program      : MTECH Software Engineering
 * Student Email: 2022MT93012@wilp.bits-pilani.ac.in
 */
package serviceorientedcomputing.assignment.authentication;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The <code>UserRepository</code> is data access object that manages
 * persistence of CustomerEntity 
 * It provides functionality to create and retrieve, update and delete
 * UserEntity from database.
 * @author Yogesh Kshatriya
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * Gets user by username
     * @param username      : specified username
     * @param password      : specified password
     * @return              : instance of Optional<UserEntity>
     */
    public Optional<UserEntity> findByUsernameAndPassword(String username, String password);
}
