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


import org.apache.commons.lang.StringUtils;

public class NullParameter<T> implements Parameter<T> {

    public static final Parameter INSTANCE = new NullParameter();

    private NullParameter() {
        // do nothing
    }

    @Override
    public T getValue() {
        return (T)new Object();
    }

    @Override
    public String getName() {
        return StringUtils.EMPTY;
    }

    @Override
    public void update(T value) {
        // do nothing
    }

    @Override
    public Parameter<T> def(T value) {
        return INSTANCE;
    }

    @Override
    public boolean itsMe(String name) {
        return false;
    }

    @Override
    public <V> Parameter<V> type(Class<V> integerClass) {
        return INSTANCE;
    }

    @Override
    public void select(int index) {
        // do nothing
    }
}
