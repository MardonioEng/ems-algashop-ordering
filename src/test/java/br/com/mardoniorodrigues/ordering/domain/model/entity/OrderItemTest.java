package br.com.mardoniorodrigues.ordering.domain.model.entity;

import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Product;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Quantity;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.OrderId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertWith;
import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    public void shouldGenerateBrandNewOrderItem() {

        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(1);
        OrderId orderId = new OrderId();

        OrderItem orderItem = OrderItem.brandNew()
                .product(product)
                .quantity(quantity)
                .orderId(orderId)
                .build();

        assertWith(orderItem,
            o -> assertThat(o.id()).isNotNull(),
            o -> assertThat(o.productId()).isEqualTo(product.id()),
            o -> assertThat(o.productName()).isEqualTo(product.name()),
            o -> assertThat(o.quantity()).isEqualTo(quantity),
            o -> assertThat(o.price()).isEqualTo(product.price()),
            o -> assertThat(o.orderId()).isEqualTo(orderId)
        );
    }
}