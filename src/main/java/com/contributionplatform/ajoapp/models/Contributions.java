package com.contributionplatform.ajoapp.models;


import com.contributionplatform.ajoapp.enums.PaymentType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Contributions implements Serializable {

    @Id
    @GeneratedValue
    private Long contributionsId;

    @Column(nullable = false)
    private Date datePaid;

    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private Double amountPaid;

}