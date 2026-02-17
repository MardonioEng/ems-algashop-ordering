package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import br.com.mardoniorodrigues.ordering.domain.exception.OrderStatusCannotBeChangedException;
import br.com.mardoniorodrigues.ordering.domain.valueObject.*;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.CustomerId;
import br.com.mardoniorodrigues.ordering.domain.valueObject.id.ProductId;

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
    public void givenDraftOrder_whenChangeBillingInfo_shouldAllowChange() {

        Address address = Address.builder()
            .street("Bourdon Street")
            .number("1234")
            .neighborhood("North Ville")
            .complement("apt. 11")
            .city("Montfort")
            .state("South Carolina")
            .zipCode(new ZipCode("79911"))
            .build();

        BillingInfo billingInfo = BillingInfo.builder()
            .address(address)
            .document(new Document("225-09-1992"))
            .phone(new Phone("123-111-9911"))
            .fullName(new FullName("John", "Doe"))
            .build();

        Order order = Order.draft(new CustomerId());
        order.changeBilling(billingInfo);

        BillingInfo expectedBillingInfo = BillingInfo.builder()
                .address(address)
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .fullName(new FullName("John", "Doe"))
                .build();

        assertThat(order.billing()).isEqualTo(expectedBillingInfo);
    }

    @Test
    public void givenDraftOrder_whenChangeShippingInfo_shouldAllowInfo() {

        Address address = Address.builder()
            .street("Bourdon Street")
            .number("1234")
            .neighborhood("North Ville")
            .complement("apt. 11")
            .city("Montfort")
            .state("South Carolina")
            .zipCode(new ZipCode("79911"))
            .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
            .address(address)
            .fullName(new FullName("John", "Doe"))
            .document(new Document("112-33-2321"))
            .phone(new Phone("111-441-1244"))
            .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(1);

        order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate);

        assertWith(order,
            o -> assertThat(o.shipping()).isEqualTo(shippingInfo),
            o -> assertThat(o.shippingCost()).isEqualTo(shippingCost),
            o -> assertThat(o.expectedDeliveryDate()).isEqualTo(expectedDeliveryDate));
    }

    @Test
    public void givenDraftOrderAndDeliveryDateInThePast_whenChangeShippingInfo_shouldNotAllowInfo() {

        Address address = Address.builder()
            .street("Bourdon Street")
            .number("1234")
            .neighborhood("North Ville")
            .complement("apt. 11")
            .city("Montfort")
            .state("South Carolina")
            .zipCode(new ZipCode("79911"))
            .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
            .address(address)
            .fullName(new FullName("John", "Doe"))
            .document(new Document("112-33-2321"))
            .phone(new Phone("111-441-1244"))
            .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedDeliveryDate = LocalDate.now().minusDays(2);

        assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
            .isThrownBy(() -> order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate));
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
}
