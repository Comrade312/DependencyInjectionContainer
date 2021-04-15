package container;

import container.annotation.Inject;
import container.exceptions.BindingNotFoundException;
import container.exceptions.ConstructorNotFoundException;
import container.exceptions.ProviderCastException;
import container.exceptions.TooManyConstructorsException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InjectorImpl implements Injector {
    private final Map<Class<?>, Constructor<?>> intfConstr = new HashMap<>();
    private final Map<Class<?>, Object> intfSingleton = new HashMap<>();

    @Override
    public synchronized <T> Provider<T> getProvider(Class<T> type) {
        try {
            Provider<T> result = null;
            if (intfSingleton.containsKey(type)) {
                if (intfSingleton.get(type) == null) {
                    result = getInstanceThroughConstr(type);
                    intfSingleton.put(type, result);
                } else {
                    result = (Provider<T>) intfSingleton.get(type);
                }
            } else if (intfConstr.containsKey(type)) {
                result = getInstanceThroughConstr(type);
            }
            return result;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public synchronized <T> void bind(Class<T> intf, Class<? extends T> impl) {
        int count = 0;
        Constructor<?> resultConstructor = null;

        for (Constructor<?> constructor : impl.getConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                resultConstructor = constructor;
                count++;
            }
        }

        if (count > 1) {
            throw new TooManyConstructorsException(impl);
        } else if (count == 0) {
            try {
                resultConstructor = impl.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new ConstructorNotFoundException(impl);
            }
        }

        intfConstr.put(intf, resultConstructor);
    }

    @Override
    public synchronized <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        bind(intf, impl);
        intfSingleton.put(intf, null);
    }

    private boolean isNoArgsConstructor(Constructor<?> constructor) {
        return constructor.getParameterTypes().length == 0;
    }

    private <T> Provider<T> getInstanceThroughConstr(Class<T> classType) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = intfConstr.get(classType);
        Object resultObject;
        if (isNoArgsConstructor(constructor)) {
            resultObject = intfConstr.get(classType).newInstance();
        } else {
            ArrayList<Object> args = new ArrayList<>();
            for (Class<?> curArg : constructor.getParameterTypes()) {
                Object curArgObject = getProvider(curArg);
                if (curArgObject != null) {
                    args.add(curArgObject);
                } else {
                    throw new BindingNotFoundException(curArg);
                }
            }
            resultObject = constructor.newInstance(args.toArray());
        }

        if (!(resultObject instanceof Provider)) {
            throw new ProviderCastException(resultObject.getClass());
        } else {
            return (Provider<T>) resultObject;
        }
    }

}
