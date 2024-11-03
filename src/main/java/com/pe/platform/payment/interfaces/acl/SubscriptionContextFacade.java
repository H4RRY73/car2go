package com.pe.platform.payment.interfaces.acl;

import com.pe.platform.payment.domain.model.commands.CreateSubscriptionCommand;
import com.pe.platform.payment.domain.services.SubscriptionCommandService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class SubscriptionContextFacade {
    private final SubscriptionCommandService subscriptionCommandService;


    public SubscriptionContextFacade(SubscriptionCommandService subscriptionCommandService) {
        this.subscriptionCommandService = subscriptionCommandService;
    }


    public Long createSubscription(Long userId, Long planId, Date startDate) {

        var createSubscriptionCommand = new CreateSubscriptionCommand(planId);
        var subscription = subscriptionCommandService.handle(createSubscriptionCommand);
        if (subscription.isEmpty()) return 0L;
        return subscription.get().getId();
    }

}
