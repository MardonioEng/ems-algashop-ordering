package br.com.mardoniorodrigues.ordering.infrastructure.persistence.repository;

import br.com.mardoniorodrigues.ordering.domain.model.utility.IdGenerator;
import br.com.mardoniorodrigues.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderPersistenceEntityRepositoryIT {

    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;

    @Autowired
    OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
    }

    @Test
    public void shouldPersist() {

        long orderId = IdGenerator.generateTSID().toLong();

        OrderPersistenceEntity entity = OrderPersistenceEntity.builder()
            .id(orderId)
            .customerId(IdGenerator.generateTimeBasedUUID())
            .totalItems(2)
            .totalAmount(new BigDecimal("1000"))
            .status("DRAFT")
            .paymentMethod("CREDIT_CARD")
            .placedAt(OffsetDateTime.now())
            .build();

        orderPersistenceEntityRepository.saveAndFlush(entity);

        assertThat(orderPersistenceEntityRepository.existsById(orderId)).isTrue();
    }

    @Test
    public void shoudCount() {

        long ordersCount = orderPersistenceEntityRepository.count();

        assertThat(ordersCount).isZero();
    }
}
