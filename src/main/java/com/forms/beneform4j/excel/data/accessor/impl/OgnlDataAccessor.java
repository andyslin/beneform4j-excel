package com.forms.beneform4j.excel.data.accessor.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;

import ognl.DefaultMemberAccess;
import ognl.MemberAccess;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public class OgnlDataAccessor extends AbstractDataAccessor {

    private static final MemberAccess DEFAULT_MEMBER_ACCESS = new DefaultMemberAccess(true);

    private static final Map<String, Object> expCache = new ConcurrentHashMap<String, Object>();

    private final OgnlContext ognlContext;

    public OgnlDataAccessor() {
        this(null, null, null, null);
    }

    public OgnlDataAccessor(Object root) {
        this(null, null, root, null);
    }

    public OgnlDataAccessor(Object root, Map<String, Object> vars) {
        this(null, null, root, vars);
    }

    private OgnlDataAccessor(IDataAccessor parent, OgnlContext ognlContext, Object root, Map<String, Object> vars) {
        super(parent);
        this.ognlContext = initOnglContext(ognlContext, root, vars);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T value(String property, Class<T> cls) {
        Object tree = getOgnlTree(property);
        try {
            @SuppressWarnings("unchecked")
            T value = (T) Ognl.getValue(tree, ognlContext, ognlContext.getRoot(), cls);
            return value;
        } catch (OgnlException e) {
            throw Throw.createRuntimeException("对OGNL表达式求值出现异常:" + property, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(String property, Object value) {
        Object tree = getOgnlTree(property);
        try {
            Ognl.setValue(tree, ognlContext, ognlContext.getRoot(), value);
        } catch (OgnlException e) {
            throw Throw.createRuntimeException("设置OGNL表达式值出现异常:" + property, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVar(String key, Object value) {
        ognlContext.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataAccessor doDerive(Object value) {
        return new OgnlDataAccessor(this, ognlContext, value, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getRoot() {
        return ognlContext.getRoot();
    }

    /**
     * 解析OGNL表达式并缓存
     * 
     * @param property
     * @return
     */
    private Object getOgnlTree(String property) {
        Object tree = expCache.get(property);
        if (null == tree) {
            synchronized (expCache) {
                tree = expCache.get(property);
                if (null == tree) {
                    try {
                        tree = Ognl.parseExpression(property);
                        expCache.put(property, tree);
                    } catch (OgnlException e) {
                        Throw.throwRuntimeException("解析OGNL表达式异常:" + property, e);
                    }
                }
            }
        }
        return tree;
    }

    /**
     * 初始化OGNL上下文
     * 
     * @param ognlContext
     * @param root
     * @param vars
     * @return
     */
    private OgnlContext initOnglContext(OgnlContext ognlContext, Object root, Map<String, Object> vars) {
        OgnlContext context = ognlContext;
        if (null == context) {
            context = new OgnlContext();
            context.setMemberAccess(DEFAULT_MEMBER_ACCESS);
        }
        context.setRoot(root);
        if (null != vars) {
            context.setValues(vars);
        }
        return context;
    }
}
