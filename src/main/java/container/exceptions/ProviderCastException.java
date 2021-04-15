package container.exceptions;

public class ProviderCastException extends RuntimeException {
    public ProviderCastException() {
        super("Class cannot be cast to Provider");
    }

    public ProviderCastException(Class<?> classEx) {
        super("Class " + classEx.getName() + " cannot be cast to Provider");
    }
}
