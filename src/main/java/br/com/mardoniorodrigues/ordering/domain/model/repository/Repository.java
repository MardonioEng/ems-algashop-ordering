package br.com.mardoniorodrigues.ordering.domain.model.repository;

import br.com.mardoniorodrigues.ordering.domain.model.entity.AggregateRoot;

import java.util.Optional;

public interface Repository<T extends AggregateRoot<ID>, ID> {

    Optional<T> ofId(ID id);

    boolean exists(ID id);

    void add(T aggretateRoot);

    int count();
}
