package br.com.mardoniorodrigues.ordering.domain.model.exception;

import br.com.mardoniorodrigues.ordering.domain.model.entity.ShoppingCartId;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.ProductId;

import static br.com.mardoniorodrigues.ordering.domain.model.exception.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT;

public class ShoppingCartDoesNotContainProductException extends DomainException {

    public ShoppingCartDoesNotContainProductException(ShoppingCartId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT, id, productId));
    }
}
