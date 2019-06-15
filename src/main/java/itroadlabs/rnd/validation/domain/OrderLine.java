package itroadlabs.rnd.validation.domain;

import java.io.Serializable;
import java.util.Objects;

public class OrderLine implements Serializable {
    private String id;
    private String productCatalogCode;
    private int quantity;

    public OrderLine(String id, String productCatalogCode, int quantity) {
        this.id = id;
        this.productCatalogCode = productCatalogCode;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getProductCatalogCode() {
        return productCatalogCode;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLine)) return false;
        OrderLine orderLine = (OrderLine) o;
        return id.equals(orderLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
