package itroadlabs.rnd.validation.app;

import com.google.common.base.Preconditions;
import itroadlabs.rnd.validation.app.apimodel.CheckoutShoppingCartRequest;
import itroadlabs.rnd.validation.app.constraints.CheckoutShoppingCartRequestValidator;
import itroadlabs.rnd.validation.app.constraints.ValidationErrorsException;
import itroadlabs.rnd.validation.domain.Order;
import itroadlabs.rnd.validation.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

@Service
@Profile("imperative-way")
class OrderServiceWithSpringImperativeValidation implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;
    private final CheckoutShoppingCartRequestValidator checkoutShoppingCartRequestValidator;

    @Autowired
    public OrderServiceWithSpringImperativeValidation(OrderRepository orderRepository,
                                                      OrderFactory orderFactory,
                                                      CheckoutShoppingCartRequestValidator checkoutShoppingCartRequestValidator) {
        this.orderRepository = orderRepository;
        this.orderFactory = orderFactory;
        this.checkoutShoppingCartRequestValidator = checkoutShoppingCartRequestValidator;
    }


    @Override
    public void makeOrder(CheckoutShoppingCartRequest checkoutShoppingCartRequest) {
        Preconditions.checkNotNull(checkoutShoppingCartRequest);
        validate(checkoutShoppingCartRequest);

        Order order = orderFactory.createOrderFrom(checkoutShoppingCartRequest);
        orderRepository.add(order);
    }

    // TODO: candidate for helper class method
    private void validate(CheckoutShoppingCartRequest checkoutShoppingCartRequest) {
        Errors errors = new BeanPropertyBindingResult(checkoutShoppingCartRequest, "checkoutShoppingCartRequest");
        checkoutShoppingCartRequestValidator.validate(checkoutShoppingCartRequest, errors);
        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }
}
