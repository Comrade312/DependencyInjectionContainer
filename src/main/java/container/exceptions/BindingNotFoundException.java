package container.exceptions;

public class BindingNotFoundException extends RuntimeException {
    public BindingNotFoundException() {
        super("Bindings cannot be found");
    }

    public BindingNotFoundException(Class<?> classEx) {
        super("Binding cannot be found for " + classEx.getName());
    }
}
