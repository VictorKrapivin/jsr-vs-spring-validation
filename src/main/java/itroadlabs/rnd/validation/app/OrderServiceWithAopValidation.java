package itroadlabs.rnd.validation.app;

import com.google.common.base.Preconditions;
import itroadlabs.rnd.validation.app.apimodel.CheckoutShoppingCartRequest;
import itroadlabs.rnd.validation.app.apimodel.ValidationStepOne;
import itroadlabs.rnd.validation.app.apimodel.ValidationStepTwo;
import itroadlabs.rnd.validation.domain.Order;
import itroadlabs.rnd.validation.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Profile("aop-way")
@Validated({ValidationStepOne.class, ValidationStepTwo.class})
class OrderServiceWithAopValidation implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;

    @Autowired
    public OrderServiceWithAopValidation(OrderRepository orderRepository, OrderFactory orderFactory) {
        this.orderRepository = orderRepository;
        this.orderFactory = orderFactory;
    }

    @Override
    public void makeOrder(@Valid CheckoutShoppingCartRequest checkoutShoppingCartRequest) {
        Preconditions.checkNotNull(checkoutShoppingCartRequest);

        Order order = orderFactory.createOrderFrom(checkoutShoppingCartRequest);
        orderRepository.add(order);
    }
}
