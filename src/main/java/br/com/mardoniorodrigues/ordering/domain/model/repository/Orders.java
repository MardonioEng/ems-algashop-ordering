package br.com.mardoniorodrigues.ordering.domain.model.repository;

import br.com.mardoniorodrigues.ordering.domain.model.entity.Order;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.OrderId;

public interface Orders extends Repository<Order, OrderId> {
}
