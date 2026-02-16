package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.valueObject.id.CustomerId;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order order = Order.draft(new CustomerId());
    }
}