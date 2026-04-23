package br.com.mardoniorodrigues.ordering.infrastructure.persistence.repository;

import br.com.mardoniorodrigues.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {
}
