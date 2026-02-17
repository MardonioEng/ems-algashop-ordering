package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.valueObject.Quantity;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.OrderId;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    public void shouldGenerate() {
        OrderItem.brandNew()
            .product(ProductTestDataBuilder.aProduct().build())
            .quantity(new Quantity(1))
            .orderId(new OrderId());
    }
}