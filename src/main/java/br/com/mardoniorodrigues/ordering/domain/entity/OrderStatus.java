package br.com.mardoniorodrigues.ordering.domain.entity;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

    DRAFT,
    PLACED(DRAFT),
    PAID(PLACED),
    READY(PAID),
    CANCELED(READY, PAID, PLACED, DRAFT);

    OrderStatus(OrderStatus... previousStatuses) {
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    private final List<OrderStatus> previousStatuses;

    public boolean canChange(OrderStatus newStatus) {
        OrderStatus currentStatus = this;
        return newStatus.previousStatuses.contains(currentStatus);
    }

    public boolean canNotChangeTo(OrderStatus newStatus) {
        return !canChange(newStatus);
    }
}
