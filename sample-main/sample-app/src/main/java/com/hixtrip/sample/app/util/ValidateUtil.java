package com.hixtrip.sample.app.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 校验工具类
 */
public class ValidateUtil {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验实体类
     */
    public static <T> void validate(T t) throws Exception {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError.append(constraintViolation.getMessage()).append(";");
            }

            throw new Exception(validateError.toString());
        }
    }

    /**
     * 通过组来校验实体类
     */
    public static <T> void validate(T t, Class<?>... groups) throws Exception {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, groups);
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError.append(constraintViolation.getMessage()).append(";");
            }

            throw new Exception(validateError.toString());
        }
    }

    /**
     * 校验，错误信息自定义分隔符
     */
    public static <T> void validate(T t, String errorMsgSeparator, Class<?>... groups) throws Exception {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, groups);
        if (constraintViolations.size() > 0) {
            String errorMessage = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(errorMsgSeparator));
            throw new Exception(errorMessage);
        }
    }
}
