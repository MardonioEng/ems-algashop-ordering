package br.com.mardoniorodrigues.ordering.domain.exception;

import br.com.mardoniorodrigues.ordering.domain.valueObject.id.OrderId;

import static br.com.mardoniorodrigues.ordering.domain.exception.ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NOT_ITEMS;

public class OrderCannotBePlacedException extends DomainException{

    public OrderCannotBePlacedException(OrderId id) {
        super(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NOT_ITEMS, id));
    }
}
