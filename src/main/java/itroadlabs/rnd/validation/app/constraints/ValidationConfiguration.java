package itroadlabs.rnd.validation.app.constraints;

import itroadlabs.rnd.validation.app.apimodel.CheckoutShoppingCartRequest;
import itroadlabs.rnd.validation.app.apimodel.ValidationStepTwo;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.GenericConstraintDef;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static java.lang.annotation.ElementType.FIELD;

@Configuration
@Profile("aop-way")
class ValidationConfiguration {
    @Bean
    LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean() {
            @Override
            protected void postProcessConfiguration(javax.validation.Configuration<?> configuration) {
                HibernateValidatorConfiguration hibernateConfiguration = (HibernateValidatorConfiguration) configuration;
                ConstraintMapping constraintMapping = hibernateConfiguration.createConstraintMapping();
                configureConstraints(constraintMapping);
                hibernateConfiguration.addMapping(constraintMapping);
            }
        };
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        return localValidatorFactoryBean;
    }

    private void configureConstraints(ConstraintMapping constraintMapping) {
        constraintMapping
                .type(CheckoutShoppingCartRequest.ShoppingCartItem.class)
                .property("productCatalogCode", FIELD)
                .constraint(new GenericConstraintDef<>(ValidProductCatalogCode.class).groups(ValidationStepTwo.class))
        ;
    }
}
