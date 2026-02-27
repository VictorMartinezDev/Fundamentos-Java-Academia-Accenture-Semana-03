package com.academia.validation;

import java.util.function.Predicate;

@FunctionalInterface
interface Validator<T> {
    ValidationResult validate(T value);

    default Validator<T> and(Validator<T> other) {
        // TODO: retornar nuevo Validator que combine ambos resultados
        return value -> {
            ValidationResult r1 = this.validate(value);
            ValidationResult r2 = other.validate(value);
            return r1.merge(r2);
        };
    }

    static <T> Validator<T> from(Predicate<T> predicate, String errorMsg) {
        // TODO: retornar Validator que use el predicado
        return value -> predicate.test(value)  ? ValidationResult.valid()
                             : ValidationResult.invalid(errorMsg);
    }
}
