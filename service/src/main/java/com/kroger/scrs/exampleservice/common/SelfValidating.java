package com.kroger.scrs.exampleservice.common;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import lombok.val;

/**
 * Extend this class to support JSR 303 Bean Validation . Typically call
 * <code>validateSelf</code> in derived constructor.
 *
 * If using Lombok, include annotation to avoid including this class in
 * hashcode and equals implementation. For example:
 *
 * <pre>
 *    &#64;Value
 *    &#64;EqualsAndHashCode(callSuper = false)
 *    class MyCommand extends SelfValidating&#60;MyCommand&#62; { ... }
 * </pre>
 *
 * @param <T> the base type
 */
public abstract class SelfValidating<T> {

  private Validator validator;

  protected SelfValidating() {
    val factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Evaluates all Bean Validations on the attributes of this instance.
   */
  protected void validateSelf() {
    val violations = validator.validate(this);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
