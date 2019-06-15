package itroadlabs.rnd.validation.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Order implements Serializable {
    private String id;
    private String orderNotes;
    private Set<OrderLine> orderLines;
    private BigDecimal total;

    public Order(String id, String orderNotes, Set<OrderLine> orderLines, BigDecimal total) {
        this.id = id;
        this.orderNotes = orderNotes;
        this.orderLines = orderLines;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public Set<OrderLine> getOrderLines() {
        return Collections.unmodifiableSet(this.orderLines);
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
