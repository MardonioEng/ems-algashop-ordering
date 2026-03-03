package br.com.mardoniorodrigues.ordering.domain.model.factory;

import br.com.mardoniorodrigues.ordering.domain.model.entity.Order;
import br.com.mardoniorodrigues.ordering.domain.model.entity.OrderTestDataBuilder;
import br.com.mardoniorodrigues.ordering.domain.model.entity.PaymentMethod;
import br.com.mardoniorodrigues.ordering.domain.model.entity.ProductTestDataBuilder;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Billing;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Product;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Quantity;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Shipping;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.CustomerId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertWith;
import static org.assertj.core.api.Assertions.assertThat;

class OrderFactoryTest {

    @Test
    public void shouldGenerateFilledOrderThatCanBePlaced() {

        Shipping shipping = OrderTestDataBuilder.aShipping();
        Billing billing = OrderTestDataBuilder.aBilling();

        Product product = ProductTestDataBuilder.aProduct().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

        Quantity quantity = new Quantity(1);
        CustomerId customerId = new CustomerId();

        Order order = OrderFactory.filled(
                customerId, shipping, billing, paymentMethod, product, quantity
        );

        assertWith(order,
            o-> assertThat(o.shipping()).isEqualTo(shipping),
            o-> assertThat(o.billing()).isEqualTo(billing),
            o-> assertThat(o.paymentMethod()).isEqualTo(paymentMethod),
            o-> assertThat(o.items()).isNotEmpty(),
            o-> assertThat(o.customerId()).isNotNull(),
                o -> assertThat(o.isDraft()).isTrue()
        );

        order.place();

        assertThat(order.isPlaced()).isTrue();
    }
}