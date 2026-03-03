package br.com.mardoniorodrigues.ordering.domain.model.entity;

import br.com.mardoniorodrigues.ordering.domain.model.exception.OrderCannotBeEditedException;
import br.com.mardoniorodrigues.ordering.domain.model.exception.OrderDoesNotContainOrderItemException;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Money;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Quantity;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.CustomerId;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.OrderItemId;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertWith;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderRemoveItemTest {

    @Test
    void givenDraftOrder_whenRemoveItem_shouldRecalculate() {

        Order order = Order.draft(new CustomerId());

        order.addItem(
            ProductTestDataBuilder.aProduct().build(),
            new Quantity(2)
        );

        OrderItem orderItem1 = order.items().iterator().next();

        order.addItem(
            ProductTestDataBuilder.aProductAltRamMemory().build(),
            new Quantity(3)
        );

        order.removeItem(orderItem1.id());

        assertWith(order,
            (i) -> assertThat(i.totalAmount()).isEqualTo(new Money("600.00")),
            (i) -> assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

    @Test
    void givenDraftOrder_whenTryToRemoveNoExistingItem_shouldGenerateException() {

        Order order = OrderTestDataBuilder.anOrder().build();

        assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
            .isThrownBy(()-> order.removeItem(new OrderItemId()));

        assertWith(order,
            (i) -> assertThat(i.totalAmount()).isEqualTo(new Money("6210.00")),
            (i) -> assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

    @Test
    void givenPlacedOrder_whenTryToRemoveItem_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        assertThatExceptionOfType(OrderCannotBeEditedException.class)
            .isThrownBy(()->order.removeItem(new OrderItemId()));

        assertWith(order,
            (i) -> assertThat(i.totalAmount()).isEqualTo(new Money("6210.00")),
            (i) -> assertThat(i.totalItems()).isEqualTo(new Quantity(3))
        );
    }

}
