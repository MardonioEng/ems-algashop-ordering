package br.com.mardoniorodrigues.ordering.domain.model.repository;

import br.com.mardoniorodrigues.ordering.domain.model.entity.Customer;
import br.com.mardoniorodrigues.ordering.domain.model.valueObject.id.CustomerId;

public interface Customers extends Repository<Customer, CustomerId> {
}
