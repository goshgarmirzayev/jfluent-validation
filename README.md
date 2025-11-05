# FluentRules

FluentRules is a lightweight, expressive validation library for Java inspired by the C# FluentValidation project. It provides a fluent, type-safe API for building reusable validation rules for any object graph without relying on reflection.

## Features

- Fluent rule chaining for any type `T`
- Built-in validators for common scenarios (`NotEmpty`, `Email`, `GreaterThan`, `LessThan`, `Length`, etc.)
- Conditional validation with `When`, `Unless`, and `OnlyIf`
- Support for custom `Must` validators with rich error messages
- Nested object validation via `SetValidator` and collection validation via `SetElementValidator`
- Reusable validator classes through `AbstractValidator` inheritance
- Aggregated `ValidationResult` with all errors
- Zero runtime dependencies and Java 17 compatible

## Getting Started

Add FluentRules as a dependency (Maven coordinates shown for illustration):

```xml
<dependency>
  <groupId>com.fluentrules</groupId>
  <artifactId>fluent-rules</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

### Creating Validators

Extend `AbstractValidator<T>` and define rules with the fluent DSL:

```java
public class UserValidator extends AbstractValidator<User> {
    public UserValidator() {
        RuleFor(User::getEmail)
            .NotEmpty().Email();

        RuleFor(User::getAge)
            .GreaterThan(18).LessThan(100);

        RuleFor(User::getAddress)
            .NotNull()
            .SetValidator(new AddressValidator());
    }
}
```

### Nested Validators

```java
public class AddressValidator extends AbstractValidator<Address> {
    public AddressValidator() {
        RuleFor(Address::getCity).NotEmpty();
        RuleFor(Address::getPostalCode)
            .Length(5, 10)
            .When(addr -> "US".equals(addr.getCountry()), b -> b.Length(5));
    }
}
```

### Validating Objects

```java
User user = ...;
UserValidator validator = new UserValidator();
ValidationResult result = validator.validate(user);

if (!result.isValid()) {
    result.getErrors().forEach(error ->
        System.out.printf("%s: %s%n", error.getField(), error.getMessage()));
}
```

### Custom Validators

```java
RuleFor(User::getPassword)
    .Must((user, password) -> password != null && password.length() >= 8)
    .WithMessage("Password must be at least 8 characters long");
```

### Collections

```java
RuleForEach(User::getOrders)
    .SetElementValidator(new OrderValidator());
```

## Architecture Overview

FluentRules is organised into three logical layers to keep the API modular and approachable:

1. **Core DSL (`com.fluentrules.core`)** – orchestrates rule composition, conditional execution, and validation context state. `AbstractValidator` exposes the fluent API while `PropertyRule`, `ValidationResult`, and `ValidationContext` manage execution.
2. **Validators (`com.fluentrules.validators`)** – reusable, focused validators for common predicates such as string emptiness, comparison, collection traversal, and predicate-based checks.
3. **Examples (`com.fluentrules.examples`)** – sample models and validators that demonstrate best practices for composing complex validators, nested structures, and conditional logic.

This layout mirrors how FluentValidation separates rule declaration from validator implementations, making it straightforward to extend or replace individual pieces.

## Examples

The `examples` module contains runnable samples demonstrating validation of `User`, `Address`, and `Order` models.

## Running Tests

In restricted environments the project ships with a lightweight harness so you can execute the full test suite without contacting Maven Central:

```bash
./scripts/run-tests.sh
```

When network access is available you can rely on the official Maven build, including the full JUnit Jupiter runtime:

```bash
mvn -B test
```

## Extending

Implement the `PropertyValidator` interface to create your own reusable validators and plug them into the fluent DSL via `RuleBuilder`.

## Releasing to Maven Central

Project maintainers can follow the [publishing guide](docs/PUBLISHING.md) for a step-by-step walkthrough covering prerequisite tooling, version management, testing, and deployment to Sonatype OSSRH.

## License

Licensed under the MIT License. See [LICENSE](LICENSE).
