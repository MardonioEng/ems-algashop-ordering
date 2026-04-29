package br.com.mardoniorodrigues.ordering.infrastructure.persistence.provider;

import br.com.mardoniorodrigues.ordering.domain.model.entity.Order;
import br.com.mardoniorodrigues.ordering.domain.model.repository.Orders;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.OrderId;
import br.com.mardoniorodrigues.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import br.com.mardoniorodrigues.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import br.com.mardoniorodrigues.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdersPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository persistenceRepository;
    private final OrderPersistenceEntityAssembler assembler;

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        return Optional.empty();
    }

    @Override
    public boolean exists(OrderId orderId) {
        return false;
    }

    @Override
    public void add(Order aggretateRoot) {

        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggretateRoot);

        persistenceRepository.saveAndFlush(persistenceEntity);
    }

    @Override
    public int count() {
        return 0;
    }
}
