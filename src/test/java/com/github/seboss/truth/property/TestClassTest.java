package com.github.seboss.truth.property;

import static com.google.common.truth.Truth.ASSERT;

import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 */
public class TestClassTest {

    private TestClass testInstance;

    /**
     *
     */
    @Before
    public void setUp() {
        testInstance = new TestClass();
    }

    /**
     *
     */
    @Test
    public void shouldSetAndGetName() {
        testInstance.setName("test"); //$NON-NLS-1$

        final String name = testInstance.getName();

        ASSERT.that(name).isEqualTo("test"); //$NON-NLS-1$
    }

    /**
     *
     */
    @Test
    public void shouldSetAndGetYes() {
        testInstance.setYes(Boolean.TRUE);

        final Boolean answer = testInstance.getYes();

        ASSERT.that(answer).isTrue();
    }

    /**
     *
     */
    @Test
    public void shouldGetNumber() {
        final Integer number = testInstance.getNumber();

        ASSERT.that(number).isNull();
    }

    /**
     *
     */
    @Test
    public void shouldSetArray() {
        testInstance.setArray(null);
    }

}
