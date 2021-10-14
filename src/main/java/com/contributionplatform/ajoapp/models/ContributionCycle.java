package com.contributionplatform.ajoapp.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class ContributionCycle implements Serializable {

    @Id @GeneratedValue
    private long contributionCycleId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private int paymentStartDay;

    @Column(nullable = false)
    private int paymentEndDay;

    @OneToMany(targetEntity = Contributions.class)
    private List<Contributions> listOfContributions;

    @OneToMany(targetEntity = Contributions.class)
    private List<Requests> listOfRequests;


    @ManyToMany(targetEntity = User.class)
    private List<User> listOfCycleMembers;
}
