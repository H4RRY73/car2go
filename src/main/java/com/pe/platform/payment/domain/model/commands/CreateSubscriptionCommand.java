package com.pe.platform.payment.domain.model.commands;

import java.util.Date;

public record CreateSubscriptionCommand(Long planId){

    public CreateSubscriptionCommand {
        if (planId == null) {
            throw new IllegalArgumentException("Plan ID cannot be null");
        }
    }

}

