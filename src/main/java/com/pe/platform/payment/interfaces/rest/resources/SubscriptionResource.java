package com.pe.platform.payment.interfaces.rest.resources;

import java.util.Date;

public record SubscriptionResource(Long userId, Long planId, Date startDate, Date endDate, Boolean paid) {

    public SubscriptionResource {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (planId == null) {
            throw new IllegalArgumentException("Plan ID cannot be null");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start Date cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End Date cannot be null");
        }
    }
}
