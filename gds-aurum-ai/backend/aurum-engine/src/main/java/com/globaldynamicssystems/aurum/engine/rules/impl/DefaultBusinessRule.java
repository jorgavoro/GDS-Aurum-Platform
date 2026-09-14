package com.globaldynamicssystems.aurum.engine.rules.impl;

import com.globaldynamicssystems.aurum.engine.rules.BusinessRule;
import com.globaldynamicssystems.aurum.engine.rules.RuleContext;
import com.globaldynamicssystems.aurum.engine.rules.RuleResult;
import org.springframework.stereotype.Component;

/**
 * Implementación inicial y genérica de {@link BusinessRule} para comprobar la detección
 * y ejecución funcional del motor de reglas de negocio en el contenedor de Spring.
 */
@Component
public class DefaultBusinessRule implements BusinessRule {

    private static final String RULE_CODE = "DEFAULT_RULE";

    /**
     * Constructor por defecto.
     */
    public DefaultBusinessRule() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return RULE_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(RuleContext context) {
        return context != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleResult evaluate(RuleContext context) {
        return RuleResult.success(RULE_CODE);
    }
}