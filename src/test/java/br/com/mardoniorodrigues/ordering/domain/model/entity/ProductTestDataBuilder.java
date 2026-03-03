package br.com.mardoniorodrigues.ordering.domain.model.entity;

import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Money;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Product;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.ProductName;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.ProductId;

public class ProductTestDataBuilder {

    public static final ProductId DEFAULT_PRODUCT_ID = new ProductId();

    private ProductTestDataBuilder() {}

    public static Product.ProductBuilder aProduct() {

        return Product.builder()
            .id(DEFAULT_PRODUCT_ID)
            .inStock(true)
            .name(new ProductName("Notebook X11"))
            .price(new Money("3000"));
    }

    public static Product.ProductBuilder aProductUnavailable() {

        return Product.builder()
            .id(DEFAULT_PRODUCT_ID)
            .inStock(false)
            .name(new ProductName("Notebook FX900"))
            .price(new Money("5000"));
    }

    public static Product.ProductBuilder aProductAltRamMemory() {

        return Product.builder()
            .id(DEFAULT_PRODUCT_ID)
            .inStock(true)
            .name(new ProductName("4GB RAM"))
            .price(new Money("200"));
    }

    public static Product.ProductBuilder aProductAltMousePad() {

        return Product.builder()
            .id(DEFAULT_PRODUCT_ID)
            .inStock(true)
            .name(new ProductName("Mouse Pad"))
            .price(new Money("100"));
    }
}


