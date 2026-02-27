package com.academia.validation;

public class ValidatorDemo {
    public static void main(String[] args) {
        // Validadores individuales
        Validator<String> notBlank = Validator.from(
            s -> s != null && !s.isBlank(), "No debe estar vacio");
        Validator<String> maxLen50 = Validator.from(
            s -> s.length() <= 50, "Maximo 50 caracteres");
        Validator<String> hasAt = Validator.from(
            s -> s.contains("@"), "Email debe contener @");
        Validator<Integer> minAge = Validator.from(
            a -> a >= 18, "Edad minima: 18");
        Validator<Integer> maxAge = Validator.from(
            a -> a <= 120, "Edad maxima: 120");

        // Validadores compuestos
        Validator<String> nameValidator = notBlank.and(maxLen50);
        Validator<String> emailValidator = notBlank.and(hasAt);
        Validator<Integer> ageValidator = minAge.and(maxAge);

        // Validador de User completo
        Validator<User> userValidator = user -> {
            ValidationResult nameResult = nameValidator.validate(user.name());
            ValidationResult emailResult = emailValidator.validate(user.email());
            ValidationResult ageResult = ageValidator.validate(user.age());
            // TODO: usar ageValidator.validate(user.age()) y combinar los 3
            ValidationResult ageActual = ageValidator.validate(user.age());
            return nameResult.merge(emailResult).merge(ageActual);
        };

        System.out.println("=== Validacion de Strings ===");
        System.out.println("Nombre 'Ana': " + nameValidator.validate("Ana"));
        System.out.println("Nombre '': " + nameValidator.validate(""));

        System.out.println("\n=== Validacion de Email ===");
        System.out.println("Email 'ana@mail.com': " + emailValidator.validate("ana@mail.com"));
        System.out.println("Email 'invalido': " + emailValidator.validate("invalido"));

        System.out.println("\n=== Validacion de Edad ===");
        System.out.println("Edad 25: " + ageValidator.validate(25));
        System.out.println("Edad 15: " + ageValidator.validate(15));
        System.out.println("Edad 150: " + ageValidator.validate(150));

        System.out.println("\n=== Validacion de User Completo ===");
        User valid = new User("Ana Garcia", "ana@mail.com", 25);
        User invalid = new User("", "sinArroba", 15);
        System.out.println("User valido: " + userValidator.validate(valid));
        System.out.println("User invalido: " + userValidator.validate(invalid));
    }
}