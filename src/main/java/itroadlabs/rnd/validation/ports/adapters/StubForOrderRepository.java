package itroadlabs.rnd.validation.ports.adapters;

import itroadlabs.rnd.validation.domain.Order;
import itroadlabs.rnd.validation.domain.OrderRepository;
import org.springframework.stereotype.Component;

@Component
class StubForOrderRepository implements OrderRepository {
    @Override
    public void add(Order order) {
    }
}
