package itroadlabs.rnd.validation.ports.adapters;

import itroadlabs.rnd.validation.app.OrderService;
import itroadlabs.rnd.validation.app.apimodel.CheckoutShoppingCartRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/shopping-cart")
class RESTfulApiShoppingCartHandler {
    private final OrderService orderService;

    public RESTfulApiShoppingCartHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/checkout", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkoutCart(@RequestBody CheckoutShoppingCartRequest request) {
        orderService.makeOrder(request);
    }
}
