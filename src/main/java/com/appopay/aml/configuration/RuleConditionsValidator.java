package com.appopay.aml.configuration;

import com.appopay.aml.model.rule.RuleConditionsDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RuleConditionsValidator implements ConstraintValidator<ValidRuleConditions, RuleConditionsDTO> {

    @Override
    public boolean isValid(RuleConditionsDTO dto, ConstraintValidatorContext context) {
        // Ensure field, checkConstraint, and value are not null
        if (dto.getField() == null || dto.getCheckConstraint() == null || dto.getValue() == null) {
            return false;
        }

        // Conditional validation logic based on 'field'
        switch (dto.getField()) {
            case "TRANSACTION_FREQUENCY":
                return validateTransactionFrequency(dto, context);

            case "IP_LOCATION":
                return validateIpLocation(dto, context);

            // Add more cases as needed for other fields
            default:
                return true;
        }
    }

    private boolean validateTransactionFrequency(RuleConditionsDTO dto, ConstraintValidatorContext context) {
        // checkConstraint must be GREATER_THAN or LESS_THAN
        if (!dto.getCheckConstraint().equals("GREATER_THAN") && !dto.getCheckConstraint().equals("LESS_THAN")) {
            addConstraintViolation(context, "For TRANSACTION_FREQUENCY, checkConstraint must be GREATER_THAN or LESS_THAN");
            return false;
        }

        // value must be numeric
        try {
            Integer.parseInt(dto.getValue());
        } catch (NumberFormatException e) {
            addConstraintViolation(context, "For TRANSACTION_FREQUENCY, value must be a number");
            return false;
        }

        return true;
    }

    private boolean validateIpLocation(RuleConditionsDTO dto, ConstraintValidatorContext context) {
        // Example: Check constraint can be "EQUALS" or "NOT_EQUALS" for IP_LOCATION
        if (!dto.getCheckConstraint().equals("EQUALS") && !dto.getCheckConstraint().equals("NOT_EQUALS")) {
            addConstraintViolation(context, "For IP_LOCATION, checkConstraint must be EQUALS or NOT_EQUALS");
            return false;
        }

        // value should be an IP address (basic regex validation)
        if (!dto.getValue().matches("^([0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            addConstraintViolation(context, "For IP_LOCATION, value must be a valid IP address");
            return false;
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
