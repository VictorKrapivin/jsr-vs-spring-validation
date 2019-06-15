package itroadlabs.rnd.validation.app;

import itroadlabs.rnd.validation.app.apimodel.CheckoutShoppingCartRequest;

import javax.validation.Valid;

public interface OrderService {
    void makeOrder(@Valid CheckoutShoppingCartRequest checkoutShoppingCartRequest);
}
