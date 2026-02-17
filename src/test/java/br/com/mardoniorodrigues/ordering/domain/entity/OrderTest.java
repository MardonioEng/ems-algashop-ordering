package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import br.com.mardoniorodrigues.ordering.domain.exception.OrderStatusCannotBeChangedException;
import br.com.mardoniorodrigues.ordering.domain.exception.ProductOutOfStockException;
import br.com.mardoniorodrigues.ordering.domain.valueObject.*;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.CustomerId;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.ProductId;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();
        ProductId productId = product.id();

        order.addItem(product, new Quantity(1));

        assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        assertWith(orderItem,
            (i) -> assertThat(i.id()).isNotNull(),
            (i) -> assertThat(i.productName()).isEqualTo(new ProductName("Mouse Pad")),
            (i) -> assertThat(i.productId()).isEqualTo(productId),
            (i) -> assertThat(i.price()).isEqualTo(new Money("100")),
            (i) -> assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    public void shouldGenerateExceptionWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        order.addItem(product, new Quantity(1));

        Set<OrderItem> item = order.items();

        assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(item::clear);
    }

    @Test
    public void shouldRecalculateTotals() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
            ProductTestDataBuilder.aProductAltMousePad().build(),
            new Quantity(2)
        );

        order.addItem(
            ProductTestDataBuilder.aProductAltRamMemery().build(),
            new Quantity(1)
        );

        assertThat(order.totalAmount()).isEqualTo(new Money("400"));
        assertThat(order.totalItems()).isEqualTo(new Quantity(3));
    }

    @Test
    public void givenDraftOrder_whenPlace_shouldChangeToPlaced() {

        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();

        assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenMarkAsPaid_shouldChangeToPaid() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        order.markAsPaid();

        assertThat(order.isPaid()).isTrue();
        assertThat(order.paidAt()).isNotNull();
    }

    @Test
    public void givenPlacedOrder_whenTryPlace_shouldGenerateException() {

        Order order = OrderTestDataBuilder.anOrder()
            .status(OrderStatus.PLACED)
            .build();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
            .isThrownBy(order::place);
    }

    @Test
    public void givenDraftOrder_whenChangePaymentMethod_shouldAllowChange() {

        Order order = Order.draft(new CustomerId());
        order.changePaymentMethod(PaymentMethod.CREDIT_CARD);

        assertWith(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

    @Test
    public void givenDraftOrder_whenChangeBilling_shouldAllowChange() {

        Billing billing = OrderTestDataBuilder.aBilling();

        Order order = Order.draft(new CustomerId());
        order.changeBilling(billing);

        assertThat(order.billing()).isEqualTo(billing);
    }

    @Test
    public void givenDraftOrder_whenChangeShipping_shouldAllowInfo() {

        Shipping shipping = OrderTestDataBuilder.aShipping();

        Order order = Order.draft(new CustomerId());

        order.changeShipping(shipping);

        assertWith(order,
            o -> assertThat(o.shipping()).isEqualTo(shipping));
    }

    @Test
    public void givenDraftOrderAndDeliveryDateInThePast_whenChangeShipping_shouldNotAllowInfo() {

        LocalDate expectedDeliveryDate = LocalDate.now().minusDays(2);

        Shipping shipping = OrderTestDataBuilder.aShipping().toBuilder()
            .expectedDate(expectedDeliveryDate)
            .build();

        Order order = Order.draft(new CustomerId());

        assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
            .isThrownBy(() -> order.changeShipping(shipping));
    }

    @Test
    public void givenDraftOrder_whenChangeItem_shouldRecalculate() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
            ProductTestDataBuilder.aProduct().build(),
            new Quantity(3)
        );

        OrderItem orderItem = order.items().iterator().next();

        order.changeItemQuantity(orderItem.id(), new Quantity(5));

        assertWith(order,
            o -> assertThat(o.totalAmount()).isEqualTo(new Money("15000.00")),
            o -> assertThat(o.totalItems()).isEqualTo(new Quantity(5))
        );
    }

    @Test
    public void givenOutOfStockProduct_whenTryAddToAnOrder_shouldNotAllow() {
        Order order = Order.draft(new CustomerId());

        ThrowableAssert.ThrowingCallable addItemTask = () -> order
            .addItem(ProductTestDataBuilder
            .aProductUnavailable().build(), new Quantity(1));

        assertThatExceptionOfType(ProductOutOfStockException.class)
            .isThrownBy(addItemTask);
    }
}
