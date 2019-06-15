package itroadlabs.rnd.validation.app.constraints;

import itroadlabs.rnd.validation.app.ProductCatalog;
import itroadlabs.rnd.validation.app.apimodel.CheckoutShoppingCartRequest;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckoutShoppingCartRequestValidatorTests {
    @Test
    public void invalidProductCatalogCodeGivesErrors() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        when(productCatalog.find(anyString())).thenReturn(Optional.empty());
        CheckoutShoppingCartRequestValidator validator = new CheckoutShoppingCartRequestValidator(productCatalog, null);

        CheckoutShoppingCartRequest request = CheckoutShoppingCartRequest.builder()
                .items(Arrays.asList(
                        new CheckoutShoppingCartRequest.ShoppingCartItem("1", 10),
                        new CheckoutShoppingCartRequest.ShoppingCartItem("2", 10)
                ))
                .build();

        Errors errors = new BeanPropertyBindingResult(request, "request");
        ValidationUtils.invokeValidator(validator, request, errors);

        assertEquals(2, errors.getErrorCount());
    }
}
