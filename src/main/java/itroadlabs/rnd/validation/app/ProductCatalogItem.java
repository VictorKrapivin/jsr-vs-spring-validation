package itroadlabs.rnd.validation.app;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductCatalogItem {
    private String name;
    private BigDecimal price;
}
