package com.contributionplatform.ajoapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

//@MappedSuperclass
@Entity(name = "users")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email
    private String emailAddress;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column
    private String phoneNumber;

    @Column
    private Date dateJoined;

    @JsonIgnore
    @OneToMany(targetEntity = Contributions.class)
    private List<Contributions> listOfContributions;

    @JsonIgnore
    @OneToMany(targetEntity = Requests.class)
    private List<Requests> listOfRequests;

    @JsonIgnore
    @ManyToMany(targetEntity = ContributionCycle.class)
    private List<ContributionCycle> listOfContributionCycle;


}
