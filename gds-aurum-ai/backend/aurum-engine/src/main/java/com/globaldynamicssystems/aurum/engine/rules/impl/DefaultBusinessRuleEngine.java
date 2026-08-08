package com.globaldynamicssystems.aurum.engine.rules.impl;

import com.globaldynamicssystems.aurum.engine.rules.BusinessRule;
import com.globaldynamicssystems.aurum.engine.rules.BusinessRuleEngine;
import com.globaldynamicssystems.aurum.engine.rules.BusinessRuleValidator;
import com.globaldynamicssystems.aurum.engine.rules.BusinessRulesResult;
import com.globaldynamicssystems.aurum.engine.rules.RuleContext;
import com.globaldynamicssystems.aurum.engine.rules.RuleExecutionException;
import com.globaldynamicssystems.aurum.engine.rules.RuleResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementación predeterminada de {@link BusinessRuleEngine} que ejecuta en secuencia
 * todas las reglas registradas que soporten el contexto proporcionado.
 */
@Component
public class DefaultBusinessRuleEngine implements BusinessRuleEngine {

    private final List<BusinessRule> rules;
    private final BusinessRuleValidator validator;

    /**
     * Constructor con inyección de dependencias por componentes Spring.
     *
     * @param rules     Lista de beans que implementan {@link BusinessRule}.
     * @param validator Componente de validación estructural del contexto.
     */
    public DefaultBusinessRuleEngine(List<BusinessRule> rules, BusinessRuleValidator validator) {
        this.rules = rules != null ? rules : List.of();
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BusinessRulesResult evaluate(RuleContext context) {
        validator.validate(context);

        BusinessRulesResult aggregateResult = new BusinessRulesResult();

        for (BusinessRule rule : rules) {
            if (rule != null && rule.supports(context)) {
                try {
                    RuleResult result = rule.evaluate(context);
                    aggregateResult.addResult(result);
                } catch (Exception e) {
                    RuleExecutionException executionException = new RuleExecutionException(
                            "Error executing rule evaluation for rule: " + rule.getCode(), e
                    );
                    aggregateResult.addResult(
                            RuleResult.failure(rule.getCode(), executionException.getMessage())
                    );
                }
            }
        }

        return aggregateResult;
    }
}