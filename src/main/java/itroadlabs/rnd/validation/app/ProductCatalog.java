package itroadlabs.rnd.validation.app;

import java.util.Optional;

public interface ProductCatalog {
    Optional<ProductCatalogItem> find(String productCatalogCode);
}
