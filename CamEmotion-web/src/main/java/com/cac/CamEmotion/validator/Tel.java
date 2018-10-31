package com.cac.CamEmotion.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tel {
	int min() default 0;

    String message();

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
