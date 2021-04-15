package testclasses.impl;

import container.annotation.Inject;
import container.Provider;
import testclasses.TestIntfOk2;
import testclasses.TestIntfOkFinal;

public class TestClassOk2 implements TestIntfOk2, Provider<TestClassOk2> {
    @Inject
    public TestClassOk2(TestIntfOkFinal a) {}

    @Override
    public TestClassOk2 getInstance() {
        return this;
    }
}
