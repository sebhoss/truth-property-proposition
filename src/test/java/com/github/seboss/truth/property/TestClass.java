/*
 * Copyright © 2015 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.seboss.truth.property;

/**
 *
 *
 */
public class TestClass {

    private String                name;
    private Boolean               yes;
    private Integer         number;
    @SuppressWarnings("unused")
    private Object[] array;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the yes
     */
    public Boolean getYes() {
        return yes;
    }

    /**
     * @param yes
     *            the yes to set
     */
    public void setYes(final Boolean yes) {
        this.yes = yes;
    }

    /**
     * @return the number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param array
     *            the array to set
     */
    public void setArray(final Object[] array) {
        this.array = array;
    }

}
