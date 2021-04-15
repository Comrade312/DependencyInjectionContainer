package testclasses.impl;

import container.annotation.Inject;
import container.Provider;
import testclasses.TestIntfOk1;
import testclasses.TestIntfOk2;

public class TestClassOk1 implements TestIntfOk1, Provider<TestClassOk1> {
    @Inject
    public TestClassOk1(TestIntfOk2 a) {}

    @Override
    public TestClassOk1 getInstance() {
        return this;
    }
}
