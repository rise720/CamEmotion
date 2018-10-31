package com.cac.CamEmotion.validator;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.context.support.ResourceBundleMessageSource;

public class TelValidator implements ConstraintValidator<Tel, String> {
	@Resource
    private ResourceBundleMessageSource messageSource;
    
    private Tel tel;
    
    @Override
    public void initialize(Tel tel) {
        this.tel = tel;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
        boolean isValid;
        
        if(value != null && value.length() >= tel.min()) {
            isValid = true;
        }
        else {
            isValid = false;
        }
        
        if(!isValid) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(tel.message()).addConstraintViolation();
        }
        return isValid;
    }
}
