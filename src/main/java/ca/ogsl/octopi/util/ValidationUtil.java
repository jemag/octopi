/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.util;

import ca.ogsl.octopi.exception.APIValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


public class ValidationUtil {

  private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public static <T> void validateBean(T objectToValidate, Class... groupsToValidate) {
    Set<ConstraintViolation<T>> constraintViolations = validator
        .validate(objectToValidate, groupsToValidate);
    if (constraintViolations.size() > 0) {
      throwValidationError(constraintViolations);
    }
  }

  private static <T> void throwValidationError(Set<ConstraintViolation<T>> constraintViolations) {
    ConstraintViolation<T> firstViolation = constraintViolations.iterator().next();
    throw new APIValidationException(firstViolation.getPropertyPath() + " " + firstViolation.getMessage());
  }
}
