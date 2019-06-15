package itroadlabs.rnd.validation.app.constraints;

import itroadlabs.rnd.validation.app.ProductCatalog;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidProductCatalogCodeValidator implements ConstraintValidator<ValidProductCatalogCode, String> {
    private ProductCatalog productCatalog;

    @Autowired
    public void setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    @Override
    public void initialize(ValidProductCatalogCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String productCatalogCode, ConstraintValidatorContext context) {
        return productCatalog.find(productCatalogCode).isPresent();
    }
}
