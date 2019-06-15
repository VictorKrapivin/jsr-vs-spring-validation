package itroadlabs.rnd.validation.app.constraints;

import itroadlabs.rnd.validation.app.ProductCatalog;
import itroadlabs.rnd.validation.app.apimodel.CheckoutShoppingCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;

@Component
@Profile("imperative-way")
public class CheckoutShoppingCartRequestValidator implements Validator {
    private final ProductCatalog productCatalog;
    private final SpringValidatorAdapter validatorAdapter;

    @Autowired
    public CheckoutShoppingCartRequestValidator(ProductCatalog productCatalog, SpringValidatorAdapter validatorAdapter) {
        this.productCatalog = productCatalog;
        this.validatorAdapter = validatorAdapter;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(CheckoutShoppingCartRequest.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CheckoutShoppingCartRequest request = (CheckoutShoppingCartRequest) o;

        if (validatorAdapter != null) {
            validatorAdapter.validate(request, errors);
        }

        List<CheckoutShoppingCartRequest.ShoppingCartItem> items = request.getItems();
        for (int i = 0; i < items.size(); i++) {
            String fieldPath = "items[" + i + "].productCatalogCode";
            String productCatalogCode = (String) errors.getFieldValue(fieldPath);
            if (!isValidProductCatalogCode(productCatalogCode)) {
                errors.rejectValue(fieldPath, "invalid.productCatalogCode.value",
                        String.format("Product '%s' does not exist in the catalog", productCatalogCode));
            }
        }
    }

    private boolean isValidProductCatalogCode(String catalogCode) {
        return productCatalog.find(catalogCode).isPresent();
    }
}
