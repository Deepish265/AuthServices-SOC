/*
 * UserEntity.java -- Defines UserEntity class 
 * This code is implemented as part of assignment   
 * course Service Oriented Computing of MTECH Program Software Engineering
 * Student Name : Deepish Sharma
 * Student Id   : 2022MT93012
 * Course       : Scalable Services
 * Program      : MTECH Software Engineering
 * Student Email: 2022MT93012@wilp.bits-pilani.ac.in
 */

package serviceorientedcomputing.assignment.authentication;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Define <code>UserEntity</code> It is persistent entity class managed by
 * spring data and JPA.
 * 
 * @author Deepish Sharma
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String role;
    private boolean enabled;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;
    
    //No constructor or setter/getter method required to be added
    //Thanks to lombok, it will dynamically generate and add to it.
    
    /**
     * Invoked on creation of new instance before persisting in database
     */
    @PrePersist
    protected void onCreate() {
        setCreatedDate(new Date());
        setUpdatedDate(getCreatedDate());
    }

    /**
     * Invoked on updating existing instance before persisting in database
     */
    @PreUpdate
    protected void onUpdate() {
        setUpdatedDate(new Date());
    }
}
