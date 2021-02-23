package com.kamila.food.core.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { })
@PositiveOrZero
public @interface TaxaFrete {
	
	/*
	 * @OverridesAttribute - Indica q essa propriedade sobrescreve a propriedade "message" da classe PositiveOrZero
	 * Porém não há funcionamento esperado, caso haja em messages.properties uma mensagem para PositiveOrZero
	 * esta é a q será retornada.
	 */
	@OverridesAttribute(constraint = PositiveOrZero.class, name = "message") 
	String message() default "{TaxaFrete.invalida}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
