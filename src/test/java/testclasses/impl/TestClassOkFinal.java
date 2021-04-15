package testclasses.impl;

import container.Provider;
import testclasses.TestIntfOkFinal;

public class TestClassOkFinal implements TestIntfOkFinal, Provider<TestClassOkFinal> {
    public TestClassOkFinal(){}

    @Override
    public TestClassOkFinal getInstance() {
        return this;
    }
}
