package br.com.mardoniorodrigues.ordering.domain.exception;

import br.com.mardoniorodrigues.ordering.domain.entity.OrderStatus;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.OrderId;

import static br.com.mardoniorodrigues.ordering.domain.exception.ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED;

public class OrderCannotBeEditedException extends DomainException{

    public OrderCannotBeEditedException(OrderId id, OrderStatus status) {
        super(String.format(ERROR_ORDER_CANNOT_BE_EDITED, id, status));
    }
}
