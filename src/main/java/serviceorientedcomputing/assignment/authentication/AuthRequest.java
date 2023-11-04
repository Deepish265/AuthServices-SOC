/*
 * AuthRquest.java -- Defines AuthRequest class 
 * This code is implemented as part of assignment given to group #3 for  
 * course Service Oriented Computing of MTECH Program Software Engineering
 * Student Name : Deepish Sharma
 * Student Id   : 2022MT93012
 * Course       : Scalable Services
 * Program      : MTECH Software Engineering
 * Student Email: 2022MT93012@wilp.bits-pilani.ac.in
 */
package serviceorientedcomputing.assignment.authentication;

import lombok.Data;

/**
 * The <code>AuthRequest</code> is plain java object used to hold username and 
 * password 
 * @author Deepish Sharma
 */
@Data
public class AuthRequest {
    private String username;
    private String password;
    
    //No constructor or setter/getter method required to be added
    //Thanks to lombok library, it will dynamically add them.
}
