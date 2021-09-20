package com.contributionplatform.ajoapp.models;

import com.contributionplatform.ajoapp.enums.RequestStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Requests implements Serializable {
    @Id
    @GeneratedValue
    private long requestId;

    @Column(nullable = false)
    private Date dateOfRequest;

    @Column(nullable = false)
    private RequestStatus requestStatus;

}
