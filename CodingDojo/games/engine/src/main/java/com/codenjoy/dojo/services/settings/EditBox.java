package com.codenjoy.dojo.services.settings;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


public class EditBox<T> implements Parameter<T> {

    private String name;
    private T def;
    private T value;
    private Class<?> type;

    public EditBox(String name) {
        this.name = name;
    }

    @Override
    public T getValue() {
        return (value == null)?def:value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void update(T value) {
        if (value instanceof String) {
            if (Integer.class.equals(type)) {
                this.value = (T)Integer.valueOf((String)value);  // TODO потестить это
            }
        } else {
            this.value = value;
        }
    }

    @Override
    public Parameter<T> def(T value) {
        def = value;
        return this;
    }

    @Override
    public boolean itsMe(String name) {
        return this.name.equals(name);
    }

    @Override
    public <V> Parameter<V> type(Class<V> type) {
        this.type = type; // TODO сделать это же с другими элементами
        return (Parameter<V>)this;
    }

    @Override
    public void select(int index) {
        throw new UnsupportedOperationException();
    }
}
