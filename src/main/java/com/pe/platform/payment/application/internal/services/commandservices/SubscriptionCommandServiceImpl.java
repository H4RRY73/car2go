package com.pe.platform.payment.application.internal.services.commandservices;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.payment.domain.model.aggregates.Subscription;
import com.pe.platform.payment.domain.model.commands.CreateSubscriptionCommand;
import com.pe.platform.payment.domain.model.commands.UpdateSubscriptionCommand;
import com.pe.platform.payment.domain.model.entities.Plan;
import com.pe.platform.payment.domain.services.SubscriptionCommandService;
import com.pe.platform.payment.infrastructure.persistence.jpa.PlanRepository;
import com.pe.platform.payment.infrastructure.persistence.jpa.SubscriptionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository, PlanRepository planRepository)
    {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    @Override
    public Optional<Subscription> handle(CreateSubscriptionCommand command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        boolean hasRequiredRole = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SELLER"));

        if (hasRequiredRole) {
            Optional<Subscription> existingSubscription = subscriptionRepository.findByUserId(userDetails.getId());
            if (existingSubscription.isPresent()) {
                throw new IllegalStateException("A subscription already exists for this user");
            }

            Optional<Plan> planOptional = planRepository.findById(command.planId());
            if (planOptional.isEmpty()) {
                throw new IllegalArgumentException("Plan doesn't exist");
            }

            LocalDate startLocalDate = LocalDate.now();
            LocalDate endLocalDate = startLocalDate.plusDays(30);
            Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            var subscription = new Subscription(userDetails.getId(),command.planId(),startDate,endDate);
            subscription.setPaid(true);
            subscriptionRepository.save(subscription);
            return Optional.of(subscription);
        } else {
            throw new SecurityException(" Only sellers can create a subscription");
        }
    }
    @Override
    public Optional<Subscription> handle(UpdateSubscriptionCommand command) {
        var subscription = subscriptionRepository.findByUserId(command.profileId());
        if (subscription.isEmpty()){
            throw new IllegalArgumentException("Subscription doesn't already exists");

        }
        subscription.get().updateToPaid();
        var updatedSubscription = subscriptionRepository.save(subscription.get());
        return Optional.of(updatedSubscription);

    }
}
