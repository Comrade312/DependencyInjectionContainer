package testclasses.impl;

import container.annotation.Inject;
import testclasses.TestIntfForManyConstructors;

public class TestClassTooManyConstructors implements TestIntfForManyConstructors {
    public TestClassTooManyConstructors() {
    }

    @Inject
    public TestClassTooManyConstructors(int a) {
    }

    @Inject
    public TestClassTooManyConstructors(int a, int b) {
    }
}
