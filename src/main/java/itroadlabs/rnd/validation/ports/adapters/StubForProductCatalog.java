package itroadlabs.rnd.validation.ports.adapters;

import itroadlabs.rnd.validation.app.ProductCatalog;
import itroadlabs.rnd.validation.app.ProductCatalogItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
class StubForProductCatalog implements ProductCatalog {
    @Override
    public Optional<ProductCatalogItem> find(String productCatalogCode) {
        if ("F000222003".equals(productCatalogCode)) {
            return Optional.of(new ProductCatalogItem("TV", BigDecimal.valueOf(1000)));
        }
        return Optional.empty();
    }
}
