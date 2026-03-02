package br.com.mardoniorodrigues.ordering.domain.exception;

import br.com.mardoniorodrigues.ordering.domain.entity.ShoppingCartId;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.ShoppingCartItemId;

import static br.com.mardoniorodrigues.ordering.domain.exception.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM;

public class ShoppingCartDoesNotContainItemException extends DomainException {

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId shoppingCartItemId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, shoppingCartItemId));
    }
}
