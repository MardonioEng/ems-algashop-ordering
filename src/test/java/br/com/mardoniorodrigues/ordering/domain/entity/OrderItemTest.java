package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.valueObject.Money;
import br.com.mardoniorodrigues.ordering.domain.valueObject.ProductName;
import br.com.mardoniorodrigues.ordering.domain.valueObject.Quantity;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.OrderId;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.ProductId;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    public void shouldGenerate() {
        OrderItem orderItem = OrderItem.brandNew().productId(new ProductId())
            .quantity(new Quantity(1))
            .orderId(new OrderId())
            .productName(new ProductName("Mouse Gamer"))
            .price(new Money("100")).build();
    }

}