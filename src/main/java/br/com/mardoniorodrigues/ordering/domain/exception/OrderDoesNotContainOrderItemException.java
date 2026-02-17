package br.com.mardoniorodrigues.ordering.domain.exception;

import br.com.mardoniorodrigues.ordering.domain.valueObject.id.OrderId;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.OrderItemId;

import static br.com.mardoniorodrigues.ordering.domain.exception.ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAIN_ITEM;

public class OrderDoesNotContainOrderItemException extends DomainException{

    public OrderDoesNotContainOrderItemException(OrderId id, OrderItemId orderItemId) {
        super(String.format(ERROR_ORDER_DOES_NOT_CONTAIN_ITEM, id, orderItemId));
    }
}
