/*
 * Copyright © 2015 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.seboss.truth.property;

import static java.beans.Introspector.getBeanInfo;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.truth.DefaultSubject;
import com.google.common.truth.FailureStrategy;
import com.google.common.truth.Subject;
import com.google.common.truth.SubjectFactory;
import com.google.common.truth.TestVerb;

/**
 *
 *
 */
public class PropertySubject extends Subject<PropertySubject, Class<?>> {

    /**
     *
     */
    public static final SubjectFactory<PropertySubject, Class<?>> PROPERTIES = new PropertySubjectFactory();

    private final TestVerb                                        test;

    /**
     * @param failureStrategy
     *            The failure strategy to use
     * @param subject
     *            The subject to test
     */
    public PropertySubject(final FailureStrategy failureStrategy, final Class<?> subject) {
        super(failureStrategy, subject);
        test = new TestVerb(failureStrategy);
    }

    /**
     * @param property
     *            The property to test
     * @return A property specification in case further verification is required
     */
    public PropertySpecification allowsToRead(final String property) {
        return verify(property, PropertyDescriptor::getReadMethod);
    }

    /**
     * @param property
     *            The property to test
     * @return A property specification in case further verification is required
     */
    public PropertySpecification allowsToWrite(final String property) {
        return verify(property, PropertyDescriptor::getWriteMethod);
    }

    private PropertySpecification verify(final String property, final Function<PropertyDescriptor, Method> method) {
        // check general availability of the property/field
        test.that(getSubject()).declaresField(property);

        // check specific accessor method (getter/setter) is available
        final PropertyDescriptor descriptor = descriptor(property);
        test.that(method.apply(descriptor)).isNotNull();

        // return specification for further verification if required
        return new PropertySpecification(test, descriptor);
    }

    private PropertyDescriptor descriptor(final String property) {
        try {
            return Stream.of(getBeanInfo(getSubject()).getPropertyDescriptors())
                    .filter(descriptor -> property.equals(descriptor.getName()))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        } catch (final IntrospectionException exception) {
            throw new AssertionError(exception);
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @SuppressWarnings("deprecation")
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (test == null ? 0 : test.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#@SuppressWarnings("deprecation") equals(java.lang.Object)
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropertySubject other = (PropertySubject) obj;
        if (test == null) {
            if (other.test != null) {
                return false;
            }
        } else if (!test.equals(other.test)) {
            return false;
        }
        return true;
    }

    /**
     *
     *
     */
    public static final class PropertySpecification {

        private final TestVerb           test;
        private final PropertyDescriptor property;

        /**
         * @param test
         *            The test verb to use for verification
         * @param property
         *            The property to specify/verify
         */
        PropertySpecification(final TestVerb test, final PropertyDescriptor property) {
            this.test = test;
            this.property = property;
        }

        /**
         * @param type
         *            The expected return type
         * @return The current property specification for method chaining
         */
        public PropertySpecification withReturnType(final Type type) {
            final Subject<DefaultSubject, Object> expectedType = test.that(type);
            Optional.of(property.getReadMethod())
                    .map(Method::getGenericReturnType)
                    .ifPresent(expectedType::isEqualTo);
            return this;
        }

        /**
         * @param type
         *            The expected parameter type
         * @return The current property specification for method chaining
         */
        public PropertySpecification withParameterType(final Type type) {
            final Subject<DefaultSubject, Object> expectedType = test.that(type);
            Optional.of(property.getWriteMethod())
                    .map(Method::getGenericParameterTypes)
                    .map(types -> types[0])
                    .ifPresent(expectedType::isEqualTo);
            return this;
        }

        /**
         * @return The current property specification for method chaining
         */
        public PropertySpecification readOnly() {
            test.that(property.getWriteMethod()).isNull();
            return this;
        }

        /**
         * @return The current property specification for method chaining
         */
        public PropertySpecification writeOnly() {
            test.that(property.getReadMethod()).isNull();
            return this;
        }

    }

    private static final class PropertySubjectFactory extends SubjectFactory<PropertySubject, Class<?>> {

        PropertySubjectFactory() {
            // no synthetic accessor
        }

        @Override
        public PropertySubject getSubject(final FailureStrategy fs, final Class<?> that) {
            return new PropertySubject(fs, that);
        }

    }
}
