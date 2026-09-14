package com.globaldynamicssystems.aurum.engine.rules.impl;

import com.globaldynamicssystems.aurum.engine.rules.BusinessRuleValidator;
import com.globaldynamicssystems.aurum.engine.rules.RuleContext;
import org.springframework.stereotype.Component;

/**
 * Implementación predeterminada de {@link BusinessRuleValidator} encargada de verificar
 * la validez de los datos requeridos en un {@link RuleContext}.
 */
@Component
public class DefaultBusinessRuleValidator implements BusinessRuleValidator {

    /**
     * Constructor por defecto.
     */
    public DefaultBusinessRuleValidator() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(RuleContext context) {
        if (context == null) {
            throw new IllegalArgumentException("RuleContext cannot be null.");
        }

        if (context.getEntityCode() == null) {
            throw new IllegalArgumentException("entityCode cannot be null.");
        }

        if (context.getEntityCode().trim().isEmpty()) {
            throw new IllegalArgumentException("entityCode cannot be empty.");
        }

        if (context.getValues() == null) {
            throw new IllegalArgumentException("values cannot be null.");
        }
    }
}