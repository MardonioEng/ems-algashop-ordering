package br.com.mardoniorodrigues.ordering.infrastructure.persistence.repository;

import br.com.mardoniorodrigues.ordering.domain.model.utility.IdGenerator;
import br.com.mardoniorodrigues.ordering.infrastructure.persistence.entity.OrderPersistenceDataBuilder;
import br.com.mardoniorodrigues.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

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

        OrderPersistenceEntity entity = OrderPersistenceDataBuilder.existingOrder().build();

        orderPersistenceEntityRepository.saveAndFlush(entity);

        assertThat(orderPersistenceEntityRepository.existsById(entity.getId())).isTrue();
    }

    @Test
    public void shoudCount() {

        long ordersCount = orderPersistenceEntityRepository.count();

        assertThat(ordersCount).isZero();
    }
}
