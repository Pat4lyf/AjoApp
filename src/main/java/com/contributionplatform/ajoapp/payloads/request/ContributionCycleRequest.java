package com.contributionplatform.ajoapp.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ContributionCycleRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Enter The Start Date Of The Cycle")
    private Date startDate;

    @NotNull(message = "Enter The End Date Of The Cycle")
    private Date endDate;

    @NotNull(message = "Enter The Start Date For Payment")
    private int paymentStartDay;

    @NotNull(message = "Enter The End Date For Payment")
    private int paymentEndDay;

}
