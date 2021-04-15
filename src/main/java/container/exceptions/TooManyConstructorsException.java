package container.exceptions;

public class TooManyConstructorsException extends RuntimeException {
    public TooManyConstructorsException() {
        super("Too many constructors with @Inject annotation");
    }

    public TooManyConstructorsException(Class<?> classEx) {
        super("Too many constructors with @Inject annotation in class " + classEx.getName());
    }
}
