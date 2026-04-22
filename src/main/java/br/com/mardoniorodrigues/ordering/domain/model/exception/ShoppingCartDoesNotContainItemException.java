package br.com.mardoniorodrigues.ordering.domain.model.exception;

import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.ShoppingCartId;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.ShoppingCartItemId;

import static br.com.mardoniorodrigues.ordering.domain.model.exception.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM;

public class ShoppingCartDoesNotContainItemException extends DomainException {

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId shoppingCartItemId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, shoppingCartItemId));
    }
}
