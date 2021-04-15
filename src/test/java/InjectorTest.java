import container.Injector;
import container.InjectorImpl;
import container.Provider;
import container.exceptions.BindingNotFoundException;
import container.exceptions.ConstructorNotFoundException;
import container.exceptions.ProviderCastException;
import container.exceptions.TooManyConstructorsException;
import org.junit.Test;
import testclasses.TestIntfForBindingNotFound;
import testclasses.TestIntfForManyConstructors;
import testclasses.TestIntfForNoConstructors;
import testclasses.TestIntfForProviderCastException;
import testclasses.TestIntfOk1;
import testclasses.TestIntfOk2;
import testclasses.TestIntfOkFinal;
import testclasses.impl.TestClassNoConstructors;
import testclasses.impl.TestClassOk1;
import testclasses.impl.TestClassOk2;
import testclasses.impl.TestClassOkFinal;
import testclasses.impl.TestClassProviderCastException;
import testclasses.impl.TestClassTooManyConstructors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class InjectorTest {
    @Test
    public void getProvider_positive() {
        Injector injector = new InjectorImpl();
        injector.bind(TestIntfOk1.class, TestClassOk1.class);
        injector.bind(TestIntfOk2.class, TestClassOk2.class);
        injector.bind(TestIntfOkFinal.class, TestClassOkFinal.class);
        Provider<TestIntfOk1> provider = injector.getProvider(TestIntfOk1.class);

        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(TestClassOk1.class, provider.getInstance().getClass());
    }

    @Test
    public void getProviderSingleton_positive() {
        Injector injector = new InjectorImpl();
        injector.bindSingleton(TestIntfOk1.class, TestClassOk1.class);
        injector.bind(TestIntfOk2.class, TestClassOk2.class);
        injector.bind(TestIntfOkFinal.class, TestClassOkFinal.class);
        Provider<TestIntfOk1> providerFirst = injector.getProvider(TestIntfOk1.class);
        Provider<TestIntfOk1> providerSec = injector.getProvider(TestIntfOk1.class);

        assertSame(providerFirst, providerSec);
    }

    @Test
    public void getProviderPrototype_positive() {
        Injector injector = new InjectorImpl();
        injector.bind(TestIntfOk1.class, TestClassOk1.class);
        injector.bind(TestIntfOk2.class, TestClassOk2.class);
        injector.bind(TestIntfOkFinal.class, TestClassOkFinal.class);
        Provider<TestIntfOk1> providerFirst = injector.getProvider(TestIntfOk1.class);
        Provider<TestIntfOk1> providerSec = injector.getProvider(TestIntfOk1.class);

        assertNotSame(providerFirst, providerSec);
    }



    @Test(expected = BindingNotFoundException.class)
    public void getProviderWithBindingException() {
        Injector injector = new InjectorImpl();
        injector.bind(TestIntfOk1.class, TestClassOk1.class);
        injector.bind(TestIntfOkFinal.class, TestClassOkFinal.class);
        Provider<TestIntfOk1> provider = injector.getProvider(TestIntfOk1.class);
    }

    @Test(expected = TooManyConstructorsException.class)
    public void bindObjectWithBindingException() {
        Injector injector = new InjectorImpl();
        injector.bind(TestIntfForManyConstructors.class, TestClassTooManyConstructors.class);
    }

    @Test(expected = ConstructorNotFoundException.class)
    public void bindObjectWithConstructorNotFoundException() {
        Injector injector = new InjectorImpl();
        injector.bind(TestIntfForNoConstructors.class, TestClassNoConstructors.class);
    }

    @Test(expected = ProviderCastException.class)
    public void getProviderWithProviderCastException() {
        Injector injector = new InjectorImpl();
        injector.bind(TestIntfForProviderCastException.class, TestClassProviderCastException.class);
        Provider<TestIntfForProviderCastException> provider = injector.getProvider(TestIntfForProviderCastException.class);
    }
}
