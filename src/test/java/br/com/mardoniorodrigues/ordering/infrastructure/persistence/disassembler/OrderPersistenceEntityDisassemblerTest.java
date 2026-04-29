package br.com.mardoniorodrigues.ordering.infrastructure.persistence.disassembler;

import br.com.mardoniorodrigues.ordering.domain.model.entity.Order;
import br.com.mardoniorodrigues.ordering.domain.model.entity.OrderStatus;
import br.com.mardoniorodrigues.ordering.domain.model.entity.PaymentMethod;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Money;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.Quantity;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.CustomerId;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.OrderId;
import br.com.mardoniorodrigues.ordering.infrastructure.persistence.entity.OrderPersistenceDataBuilder;
import br.com.mardoniorodrigues.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderPersistenceEntityDisassemblerTest {

    private final OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    public void shouldConvertFromPersistence() {

        OrderPersistenceEntity persistenceEntity = OrderPersistenceDataBuilder.existingOrder().build();
        Order domainEntity = disassembler.toDomainEntity(persistenceEntity);

        assertThat(domainEntity).satisfies(
            s -> assertThat(s.id()).isEqualTo(new OrderId(persistenceEntity.getId())),
            s -> assertThat(s.customerId()).isEqualTo(new CustomerId(persistenceEntity.getCustomerId())),
            s -> assertThat(s.totalAmount()).isEqualTo(new Money(persistenceEntity.getTotalAmount())),
            s -> assertThat(s.totalItems()).isEqualTo(new Quantity(persistenceEntity.getTotalItems())),
            s -> assertThat(s.placedAt()).isEqualTo(persistenceEntity.getPlacedAt()),
            s -> assertThat(s.paidAt()).isEqualTo(persistenceEntity.getPaidAt()),
            s -> assertThat(s.canceledAt()).isEqualTo(persistenceEntity.getCanceledAt()),
            s -> assertThat(s.readyAt()).isEqualTo(persistenceEntity.getReadyAt()),
            s -> assertThat(s.status()).isEqualTo(OrderStatus.valueOf(persistenceEntity.getStatus())),
            s -> assertThat(s.paymentMethod()).isEqualTo(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
        );
    }

}