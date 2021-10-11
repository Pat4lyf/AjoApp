package com.contributionplatform.ajoapp.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ContributionCycleRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Enter The Start Date Of The Cycle")
    private String startDate;

    @NotBlank(message = "Enter The End Date Of The Cycle")
    private String endDate;

    @NotBlank(message = "Enter The Start Date For Payment")
    private int paymentStartDay;

    @NotBlank(message = "Enter The End Date For Payment")
    private int paymentEndDay;

}
