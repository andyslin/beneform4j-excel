package com.forms.beneform4j.excel.data.accessor.impl;

import java.util.Map;

import com.forms.beneform4j.excel.data.accessor.IDataAccessor;

public class SpelDataAccessor extends AbstractDataAccessor {

    private final Object root;

    private final Map<String, Object> vars;

    public SpelDataAccessor() {
        this(null, null, null);
    }

    public SpelDataAccessor(Object root) {
        this(null, root, null);
    }

    public SpelDataAccessor(Object root, Map<String, Object> vars) {
        this(null, root, vars);
    }

    private SpelDataAccessor(IDataAccessor parent, Object root, Map<String, Object> vars) {
        super(parent);
        this.root = root;
        this.vars = vars;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T value(String property, Class<T> cls) {
        return Spel.getValue(root, property, vars, cls);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(String property, Object value) {
        Spel.setValue(root, property, vars, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVar(String key, Object value) {
        Spel.addVar(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getRoot() {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataAccessor doDerive(Object value) {
        return new SpelDataAccessor(this, value, vars);
    }
}
