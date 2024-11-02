package com.pe.platform.payment.domain.model.aggregates;

import com.pe.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Subscription extends AuditableAbstractAggregateRoot<Subscription> {


    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long planId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;


    @Column(nullable = false)
    private Boolean paid;


    public Subscription() {

    }

    public Subscription(Long userId, Long planId, Date startDate, Date endDate) {
        this.userId = userId;
        this.planId = planId;
        this.paid = true;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public void updateToPaid() {
        this.paid = !paid;
    }
}
