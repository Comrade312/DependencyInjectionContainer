package container.exceptions;

public class ConstructorNotFoundException extends RuntimeException {
    public ConstructorNotFoundException() {
        super("Constructors with @Inject annotation and default construction cannot be found");
    }

    public ConstructorNotFoundException(Class<?> classEx) {
        super("Constructors with @Inject annotation and default construction cannot be found in class " + classEx.getName());
    }
}
