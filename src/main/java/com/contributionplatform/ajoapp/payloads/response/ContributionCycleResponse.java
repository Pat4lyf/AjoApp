package com.contributionplatform.ajoapp.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributionCycleResponse {
    private long contributionCycleId;

    private String name;

    private Date startDate;

    private Date endDate;

    private boolean status;

    private Date paymentStartDate;

    private Date paymentEndDate;

}
