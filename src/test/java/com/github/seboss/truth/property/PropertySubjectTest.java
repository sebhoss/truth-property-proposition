/*
 * Copyright © 2015 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.seboss.truth.property;

import static com.github.seboss.truth.property.PropertySubject.PROPERTIES;
import static com.google.common.truth.Truth.ASSERT;

import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 */
@SuppressWarnings("nls")
public class PropertySubjectTest {

    private PropertySubject classUnderTest;

    /**
     *
     */
    @Before
    public void setUp() {
        classUnderTest = ASSERT.about(PROPERTIES).that(TestClass.class);
    }

    /**
     *
     */
    @Test
    public void shouldVerifyReadableProperty() {
        classUnderTest.allowsToRead("name").withReturnType(String.class);
    }

    /**
     *
     */
    @Test
    public void shouldVerifyWriteableProperty() {
        classUnderTest.allowsToWrite("yes").withParameterType(Boolean.class);
    }

    /**
     *
     */
    @Test
    public void shouldVerifyReadOnlyProperty() {
        classUnderTest.allowsToRead("number").withReturnType(Integer.class).readOnly();
    }

    /**
     *
     */
    @Test
    public void shouldVerifyWriteOnlyProperty() {
        classUnderTest.allowsToWrite("array").withParameterType(Object[].class).writeOnly();
    }

    /**
     *
     */
    @Test
    @SuppressWarnings("static-method")
    public void shouldImplementEquals() {
        final PropertySubject subject1 = ASSERT.about(PROPERTIES).that(TestClass.class);
        final PropertySubject subject2 = ASSERT.about(PROPERTIES).that(TestClass.class);

        subject1.equals(subject2);
    }

    /**
     *
     */
    @Test
    @SuppressWarnings("static-method")
    public void shouldImplementEqualsNull() {
        final PropertySubject subject1 = ASSERT.about(PROPERTIES).that(TestClass.class);

        subject1.equals(null);
    }

    /**
     *
     */
    @Test
    @SuppressWarnings("static-method")
    public void shouldImplementEqualsOtherClass() {
        final PropertySubject subject1 = ASSERT.about(PROPERTIES).that(TestClass.class);

        subject1.equals("test");
    }

    /**
     *
     */
    @Test
    @SuppressWarnings("static-method")
    public void shouldImplementHashCode() {
        final PropertySubject subject1 = ASSERT.about(PROPERTIES).that(TestClass.class);

        subject1.hashCode();
    }

}
