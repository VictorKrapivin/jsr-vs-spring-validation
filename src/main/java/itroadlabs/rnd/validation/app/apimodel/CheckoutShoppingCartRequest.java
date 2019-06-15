package itroadlabs.rnd.validation.app.apimodel;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Generated("itroadlabs.openapitools.SourceCodeGenerator")
public class CheckoutShoppingCartRequest {
    @NotNull
    @Size(min = 1)
    @UniqueElements
    private List<@Valid ShoppingCartItem> items;
    private String orderNotes;

    @Builder
    public CheckoutShoppingCartRequest(List<ShoppingCartItem> items,
                                       String orderNotes) {
        this.items = items;
        this.orderNotes = orderNotes;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class ShoppingCartItem {
        @NotBlank
        private String productCatalogCode;
        @NotNull
        @Min(1)
        private Integer quantity;
    }
}
