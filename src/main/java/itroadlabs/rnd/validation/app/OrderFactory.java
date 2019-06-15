package itroadlabs.rnd.validation.app;

import itroadlabs.rnd.validation.app.apimodel.CheckoutShoppingCartRequest;
import itroadlabs.rnd.validation.domain.Order;
import itroadlabs.rnd.validation.domain.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
class OrderFactory {
    private final ProductCatalog productCatalog;

    @Autowired
    OrderFactory(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    Order createOrderFrom(CheckoutShoppingCartRequest checkoutShoppingCartRequest) {
        BigDecimal orderTotal = BigDecimal.ZERO;
        Set<OrderLine> orderLines = new HashSet<>();
        List<CheckoutShoppingCartRequest.ShoppingCartItem> items = checkoutShoppingCartRequest.getItems();

        for (CheckoutShoppingCartRequest.ShoppingCartItem item : items) {
            orderLines.add(new OrderLine(
                    UUID.randomUUID().toString(),
                    item.getProductCatalogCode(),
                    item.getQuantity()));
            ProductCatalogItem productCatalogItem = productCatalog.find(item.getProductCatalogCode()).orElseThrow(IllegalStateException::new);
            orderTotal = orderTotal.add(productCatalogItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        return new Order(UUID.randomUUID().toString(),
                checkoutShoppingCartRequest.getOrderNotes(),
                orderLines, orderTotal);
    }
}
