package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.valueObject.Money;
import br.com.mardoniorodrigues.ordering.domain.valueObject.Product;
import br.com.mardoniorodrigues.ordering.domain.valueObject.ProductName;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.ProductId;

public class ProductTestDataBuilder {

    private ProductTestDataBuilder() {}

    public static Product.ProductBuilder aProduct() {

        return Product.builder()
            .id(new ProductId())
            .inStock(true)
            .name(new ProductName("Notebook X11"))
            .price(new Money("3000"));
    }

    public static Product.ProductBuilder aProductUnavailable() {

        return Product.builder()
            .id(new ProductId())
            .inStock(false)
            .name(new ProductName("Notebook FX900"))
            .price(new Money("5000"));
    }

    public static Product.ProductBuilder aProductAltRamMemery() {

        return Product.builder()
            .id(new ProductId())
            .inStock(true)
            .name(new ProductName("4GB RAM"))
            .price(new Money("200"));
    }

    public static Product.ProductBuilder aProductAltMousePad() {

        return Product.builder()
            .id(new ProductId())
            .inStock(true)
            .name(new ProductName("Mouse Pad"))
            .price(new Money("100"));
    }
}


