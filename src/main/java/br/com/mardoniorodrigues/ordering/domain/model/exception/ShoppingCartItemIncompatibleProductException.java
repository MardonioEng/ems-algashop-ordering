package br.com.mardoniorodrigues.ordering.domain.model.exception;

import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.ProductId;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.ShoppingCartItemId;

import static br.com.mardoniorodrigues.ordering.domain.model.exception.ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT;

public class ShoppingCartItemIncompatibleProductException extends DomainException {

    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}
