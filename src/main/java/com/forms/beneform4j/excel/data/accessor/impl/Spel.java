package com.forms.beneform4j.excel.data.accessor.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.BeanFactoryAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.expression.spel.support.StandardTypeLocator;

import com.forms.beneform4j.core.util.config.BaseConfig;
import com.forms.beneform4j.core.util.exception.Throw;

public class Spel implements ApplicationContextAware {

    /**
     * 当前线程的执行环境
     */
    private static final ThreadLocal<StandardEvaluationContext> context = new ThreadLocal<StandardEvaluationContext>();
    /**
     * 当前线程的执行变量
     */
    private static final ThreadLocal<Map<String, Object>> variables = new ThreadLocal<Map<String, Object>>();
    /**
     * 自定义变量
     */
    private static final Map<String, Object> customVariables = new HashMap<String, Object>();
    /**
     * 表达式解析器
     */
    private static final ExpressionParser expressionParser = new SpelExpressionParser();
    /**
     * 表达式缓存
     */
    private static final Map<String, Expression> expCache = new ConcurrentHashMap<String, Expression>();
    /**
     * Spring容器
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Spel.applicationContext = applicationContext;
    }

    /**
     * 表达式求值
     * 
     * @param root
     * @param expression
     * @param vars
     * @return
     */
    public static Object getValue(Object root, String expression, Map<String, Object> vars) {
        try {
            Expression expr = getExpression(expression);
            EvaluationContext evaluationContext = getEvaluationContext(vars);
            if (null == root) {
                return expr.getValue(evaluationContext);
            } else {
                return expr.getValue(evaluationContext, root);
            }
        } finally {
            resetContext();
        }
    }

    /**
     * 表达式求值
     * 
     * @param root
     * @param expression
     * @param vars
     * @param type
     * @return
     */
    public static <T> T getValue(Object root, String expression, Map<String, Object> vars, Class<T> type) {
        try {
            Expression expr = getExpression(expression);
            EvaluationContext evaluationContext = getEvaluationContext(vars);
            if (null == root) {
                return expr.getValue(evaluationContext, type);
            } else {
                return expr.getValue(evaluationContext, root, type);
            }
        } finally {
            resetContext();
        }
    }

    /**
     * 设置表达式的值
     * 
     * @param root
     * @param expression
     * @param vars
     * @param value
     */
    public static void setValue(Object root, String expression, Map<String, Object> vars, Object value) {
        try {
            Expression expr = getExpression(expression);
            EvaluationContext evaluationContext = getEvaluationContext(vars);
            if (null == root) {
                expr.setValue(evaluationContext, value);
            } else {
                expr.setValue(evaluationContext, root, value);
            }
        } finally {
            resetContext();
        }
    }

    /**
     * 添加SpEL表达式执行时的变量
     * 
     * @param name
     * @param variable
     */
    public static void addVar(String name, Object variable) {
        customVariables.put(name, variable);
    }

    /**
     * 重置上下文环境
     */
    private static void resetContext() {
        Map<String, Object> varis = variables.get();
        if (null != varis) {
            varis.clear();
        }
    }

    /**
     * 获取当前线程执行上下文
     * 
     * @param vars
     * @return
     */
    private static EvaluationContext getEvaluationContext(Map<String, Object> vars) {
        StandardEvaluationContext evaluationContext = context.get();
        if (null == evaluationContext) {
            synchronized (context) {
                evaluationContext = context.get();
                if (null == evaluationContext) {
                    evaluationContext = new StandardEvaluationContext();
                    initialStandardEvaluationContext(evaluationContext);
                    Map<String, Object> varis = getVariables(evaluationContext);
                    context.set(evaluationContext);
                    variables.set(varis);
                }
            }
        }
        evaluationContext.setVariables(customVariables);
        if (null != vars) {
            evaluationContext.setVariables(vars);
        }
        return evaluationContext;
    }

    /**
     * 从执行环境中获取变量
     * 
     * @param evaluationContext
     * @return
     */
    private static Map<String, Object> getVariables(StandardEvaluationContext evaluationContext) {
        try {
            Field variablesField = StandardEvaluationContext.class.getDeclaredField("variables");
            if (!variablesField.isAccessible()) {
                variablesField.setAccessible(true);
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> varis = (Map<String, Object>) variablesField.get(evaluationContext);
            return varis;
        } catch (Exception e) {
            throw Throw.createRuntimeException(e);
        }
    }

    /**
     * 解析SpELl表达式并缓存
     * 
     * @param expression
     * @return
     */
    private static Expression getExpression(String expression) {
        Expression expr = expCache.get(expression);
        if (expr == null) {
            synchronized (expCache) {
                expr = expCache.get(expression);
                if (expr == null) {
                    expr = expressionParser.parseExpression(expression);
                    expCache.put(expression, expr);
                }
            }
        }
        return expr;
    }

    /**
     * 初始化SpEL上下文
     * 
     * @param evaluationContext
     * @throws BeansException
     */
    private static void initialStandardEvaluationContext(StandardEvaluationContext evaluationContext) throws BeansException {
        evaluationContext.addPropertyAccessor(new BeanExpressionContextAccessor());
        evaluationContext.addPropertyAccessor(new BeanFactoryAccessor());
        evaluationContext.addPropertyAccessor(new EnvironmentAccessor());
        evaluationContext.addPropertyAccessor(new MapAccessor() {
            public boolean canRead(EvaluationContext context, Object target, String name) throws AccessException {
                return true;
            }

            public TypedValue read(EvaluationContext context, Object target, String name) throws AccessException {
                @SuppressWarnings("rawtypes")
                Map map = (Map) target;
                Object value = map.get(name);
                if (value == null) {
                    return TypedValue.NULL;
                }
                return new TypedValue(value);
            }
        });
        if (null != applicationContext && applicationContext.getAutowireCapableBeanFactory() instanceof ConfigurableBeanFactory) {
            ConfigurableBeanFactory factory = (ConfigurableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            evaluationContext.setBeanResolver(new BeanFactoryResolver(factory));
            evaluationContext.setTypeLocator(new StandardTypeLocator(factory.getBeanClassLoader()));
            ConversionService conversionService = factory.getConversionService();
            if (conversionService == null) {
                conversionService = BaseConfig.getConversionService();
            }
            evaluationContext.setTypeConverter(new StandardTypeConverter(conversionService));
        } else {
            evaluationContext.setTypeConverter(new StandardTypeConverter(BaseConfig.getConversionService()));
        }
    }
}
