package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.exception.OrderStatusCannotBeChangedException;
import br.com.mardoniorodrigues.ordering.domain.valueObject.Money;
import br.com.mardoniorodrigues.ordering.domain.valueObject.ProductName;
import br.com.mardoniorodrigues.ordering.domain.valueObject.Quantity;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.CustomerId;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.ProductId;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertWith;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order order = Order.draft(new CustomerId());
    }

    @Test
    public void shouldAddItem() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
                productId,
            new ProductName("Mouse Gamer"),
            new Money("100"),
            new Quantity(1)
        );

        assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        assertWith(orderItem,
            (i) -> assertThat(i.id()).isNotNull(),
            (i) -> assertThat(i.productName()).isEqualTo(new ProductName("Mouse Gamer")),
            (i) -> assertThat(i.productId()).isEqualTo(productId),
            (i) -> assertThat(i.price()).isEqualTo(new Money("100")),
            (i) -> assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    public void shouldGenerateExceptionWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();

        order.addItem(
            productId,
            new ProductName("Mouse Gamer"),
            new Money("100"),
            new Quantity(1)
        );

        Set<OrderItem> item = order.items();

        assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(item::clear);
    }

    @Test
    public void shouldRecalculateTotals() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
            new ProductId(),
            new ProductName("Mouse Gamer"),
            new Money("100"),
            new Quantity(2)
        );

        order.addItem(
            new ProductId(),
            new ProductName("RAM Memory"),
            new Money("50"),
            new Quantity(1)
        );

        assertThat(order.totalAmount()).isEqualTo(new Money("250"));
        assertThat(order.totalItems()).isEqualTo(new Quantity(3));
    }

    @Test
    public void givenDraftOrder_whenPlace_shouldChangeToPlaced() {

        Order order = Order.draft(new CustomerId());
        order.place();

        assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenTryPlace_shouldGenerateException() {

        Order order = Order.draft(new CustomerId());
        order.place();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
            .isThrownBy(order::place);
    }
}
