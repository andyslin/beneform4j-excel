package com.forms.beneform4j.excel.core.model.loader.anno;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.impl.extractor.BaseBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.impl.extractor.MixinBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.PositionBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.BaseBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.MixinBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.SheetBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.impl.validator.BaseBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.validator.CompositeBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.validator.MixinBeanEMValidator;
import com.forms.beneform4j.excel.core.model.loader.IEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.anno.extractor.MixinBeanEMExtractorAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.extractor.BaseBeanEMExtractorAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.BeanEMSheetMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell.PositionBeanEMMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell.MixinBeanEMMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell.BaseBeanEMMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.endloop.PositionBeanEMEndLoopMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.endloop.MixinBeanEMEndLoopMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.endloop.BaseBeanEMEndLoopMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.validator.BaseBeanEMValidatorAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.validator.MixinBeanEMValidatorAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.validator.CompositeBeanEMValidatorAnno;
import com.forms.beneform4j.excel.core.model.loader.base.AbstractCacheableEMLoader;

public class AnnoEMLoader extends AbstractCacheableEMLoader {

    @Override
    protected IEM doLoad(IEMLoadContext context, String modelId) {
        try {
            Class<?> cls = Class.forName(modelId);
            return doLoad(cls);
        } catch (Exception e) {
            return null;
        }
    }

    protected BeanEM newBeanEM(Class<?> cls) {
        BeanEM em = new BeanEM();
        em.setBeanType(cls);
        em.setId(cls.getName());
        em.setName(cls.getSimpleName());
        em.setDesc(cls.getName());
        em.setType(EMType.BEAN);
        return em;
    }

    protected BeanEMProperty newBeanEMProperty(Field field) {
        BeanEMProperty property = new BeanEMProperty();
        property.setName(field.getName());
        property.setType(field.getType());
        return property;
    }

    private IBeanEM doLoad(Class<?> cls) throws Exception {
        BeanEM em = newBeanEM(cls);
        Map<String, IBeanEMProperty> properties = new LinkedHashMap<String, IBeanEMProperty>();
        for (Class<?> c = cls; !c.equals(Object.class); c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            if (null != fields && fields.length >= 1) {
                for (Field field : fields) {
                    if (!properties.containsKey(field.getName()) && !isOmitField(field)) {
                        IBeanEMProperty value = doLoadProperty(cls, field);
                        properties.put(field.getName(), value);
                    }
                }
            }
        }
        em.setProperties(properties);
        return em;
    }

    protected IBeanEMProperty doLoadProperty(Class<?> cls, Field field) throws Exception {
        BeanEMProperty property = newBeanEMProperty(field);

        //1.匹配器
        IBeanEMMatcher matcher = doLoadMatcher(cls, field);
        if (null == matcher) {
            return null;//没有匹配器，不解析
        }
        buildMatcherSheetProperties(matcher, cls, field);
        property.setMatcher(matcher);

        //2.校验器
        IBeanEMValidator validator = doLoadValidator(cls, field);
        property.setValidator(validator);

        //3.解析器
        IBeanEMExtractor extractor = doLoadExtractor(cls, field);
        property.setExtractor(extractor);

        //4.列表类型
        if (Collection.class.isAssignableFrom(field.getType())) {
            //4.1列表结束匹配器
            IBeanEMMatcher endMatcher = doLoadEndLoopMatcher(cls, field);
            property.setEndMatcher(endMatcher);

            //4.2列表类型元数据
            Class<?> gCls = getGenericType(field);
            IBeanEM elementClassMeta = doLoad(gCls);
            property.setInnerBeanEM(elementClassMeta);
        }
        return property;
    }

    /**
     * 是否为忽略属性
     * 
     * @param field
     * @return
     */
    protected boolean isOmitField(Field field) {
        return "serialVersionUID".equals(field.getName()) || field.isAnnotationPresent(BeanEMOmitFieldAnno.class);
    }

    /**
     * 构建匹配器
     * 
     * @param cls
     * @param field
     * @return
     */
    protected IBeanEMMatcher doLoadMatcher(Class<?> cls, Field field) {
        if (field.isAnnotationPresent(PositionBeanEMMatcherAnno.class)) {
            PositionBeanEMMatcherAnno anno = field.getAnnotation(PositionBeanEMMatcherAnno.class);
            IBeanEMMatcher matcher = doLoadBeanEMMatcher(anno);
            return matcher;
        } else if (field.isAnnotationPresent(BaseBeanEMMatcherAnno.class)) {
            BaseBeanEMMatcherAnno anno = field.getAnnotation(BaseBeanEMMatcherAnno.class);
            IBeanEMMatcher matcher = doLoadBeanEMMatcher(anno);
            return matcher;
        } else if (field.isAnnotationPresent(MixinBeanEMMatcherAnno.class)) {
            MixinBeanEMMatcherAnno anno = field.getAnnotation(MixinBeanEMMatcherAnno.class);
            IBeanEMMatcher matcher = doLoadBeanEMMatcher(anno);
            return matcher;
        }
        return doLoadCustomMatcher(cls, field);
    }

    /**
     * 自定义匹配器的构建，供子类覆盖
     * 
     * @param cls
     * @param field
     * @return
     */
    protected IBeanEMMatcher doLoadCustomMatcher(Class<?> cls, Field field) {
        return null;
    }

    /**
     * 构建匹配列表结束的匹配器器
     * 
     * @param cls
     * @param field
     * @return
     */
    protected IBeanEMMatcher doLoadEndLoopMatcher(Class<?> cls, Field field) {
        if (field.isAnnotationPresent(PositionBeanEMEndLoopMatcherAnno.class)) {
            PositionBeanEMEndLoopMatcherAnno anno = field.getAnnotation(PositionBeanEMEndLoopMatcherAnno.class);
            IBeanEMMatcher matcher = doLoadBeanEMMatcher(anno.value());
            return matcher;
        } else if (field.isAnnotationPresent(BaseBeanEMEndLoopMatcherAnno.class)) {
            BaseBeanEMEndLoopMatcherAnno anno = field.getAnnotation(BaseBeanEMEndLoopMatcherAnno.class);
            IBeanEMMatcher matcher = doLoadBeanEMMatcher(anno.value());
            return matcher;
        } else if (field.isAnnotationPresent(MixinBeanEMEndLoopMatcherAnno.class)) {
            MixinBeanEMEndLoopMatcherAnno anno = field.getAnnotation(MixinBeanEMEndLoopMatcherAnno.class);
            return doLoadBeanEMMatcher(anno.value());
        }
        return doLoadCustomEndLoopMatcher(cls, field);
    }

    /**
     * 自定义匹配列表结束的匹配器的构建，供子类覆盖
     * 
     * @param cls
     * @param field
     * @return
     */
    protected IBeanEMMatcher doLoadCustomEndLoopMatcher(Class<?> cls, Field field) {
        return null;
    }

    private IBeanEMMatcher doLoadBeanEMMatcher(PositionBeanEMMatcherAnno anno) {
        PositionBeanEMMatcher matcher = new PositionBeanEMMatcher();
        matcher.setCellPosition(anno.cellPosition());
        matcher.setRowPosition(anno.rowPosition());
        return matcher;
    }

    private IBeanEMMatcher doLoadBeanEMMatcher(BaseBeanEMMatcherAnno anno) {
        BaseBeanEMMatcher matcher = new BaseBeanEMMatcher();
        matcher.setOffsetX(anno.offsetX());
        matcher.setOffsetY(anno.offsetY());
        matcher.setMergeCell(anno.isMergeCell());
        matcher.setIgnoreCase(anno.ignoreCase());
        matcher.setValue(anno.value());
        matcher.setPattern(anno.pattern());
        return matcher;
    }

    private IBeanEMMatcher doLoadBeanEMMatcher(MixinBeanEMMatcherAnno anno) {
        MixinBeanEMMatcher matcher = new MixinBeanEMMatcher();
        matcher.setClassName(anno.beanName());
        matcher.setClassType(anno.beanType());
        return matcher;
    }

    /**
     * 设置表单级别的匹配器
     * 
     * @param matcher
     * @param cls
     * @param field
     */
    private void buildMatcherSheetProperties(IBeanEMMatcher matcher, Class<?> cls, Field field) {
        if (matcher instanceof SheetBeanEMMatcher) {
            BeanEMSheetMatcherAnno es = null;
            if (field.isAnnotationPresent(BeanEMSheetMatcherAnno.class)) {
                es = field.getAnnotation(BeanEMSheetMatcherAnno.class);
            } else if (cls.isAnnotationPresent(BeanEMSheetMatcherAnno.class)) {
                es = cls.getAnnotation(BeanEMSheetMatcherAnno.class);
            }
            if (null != es) {
                SheetBeanEMMatcher m = (SheetBeanEMMatcher) matcher;
                m.setSheetIndex(es.value());
                m.setSheetName(es.name());
            }
        }
    }

    /**
     * 构建校验器
     * 
     * @param cls
     * @param field
     * @return
     */
    protected IBeanEMValidator doLoadValidator(Class<?> cls, Field field) {
        if (field.isAnnotationPresent(CompositeBeanEMValidatorAnno.class)) {
            CompositeBeanEMValidatorAnno anno = field.getAnnotation(CompositeBeanEMValidatorAnno.class);
            return doLoadValidator(anno);
        } else if (field.isAnnotationPresent(BaseBeanEMValidatorAnno.class)) {
            BaseBeanEMValidatorAnno anno = field.getAnnotation(BaseBeanEMValidatorAnno.class);
            return doLoadValidator(anno);
        } else if (field.isAnnotationPresent(MixinBeanEMValidatorAnno.class)) {
            MixinBeanEMValidatorAnno anno = field.getAnnotation(MixinBeanEMValidatorAnno.class);
            return doLoadValidator(anno);
        }
        return doLoadCustomValidator(cls, field);
    }

    private IBeanEMValidator doLoadValidator(CompositeBeanEMValidatorAnno anno) {
        CompositeBeanEMValidator validator = new CompositeBeanEMValidator();
        validator.setOr(anno.isOr());

        List<IBeanEMValidator> validators = new ArrayList<IBeanEMValidator>();
        MixinBeanEMValidatorAnno[] mixins = anno.mixins();
        if (null != mixins) {
            for (MixinBeanEMValidatorAnno proxy : mixins) {
                validators.add(doLoadValidator(proxy));
            }
        }

        BaseBeanEMValidatorAnno[] valids = anno.value();
        if (null != mixins) {
            for (BaseBeanEMValidatorAnno valid : valids) {
                validators.add(doLoadValidator(valid));
            }
        }

        if (validators.isEmpty()) {
            return null;
        } else {
            validator.setValidators(validators);
            return validator;
        }
    }

    private IBeanEMValidator doLoadValidator(BaseBeanEMValidatorAnno anno) {
        BaseBeanEMValidator validate = new BaseBeanEMValidator();
        validate.setOffsetX(anno.offsetX());
        validate.setOffsetY(anno.offsetY());
        validate.setIgnoreCase(anno.ignoreCase());
        validate.setAllowEmpty(anno.allowEmpty());
        validate.setValue(anno.value());
        validate.setPattern(anno.pattern());
        return validate;
    }

    private IBeanEMValidator doLoadValidator(MixinBeanEMValidatorAnno anno) {
        MixinBeanEMValidator validator = new MixinBeanEMValidator();
        validator.setClassName(anno.beanName());
        validator.setClassType(anno.beanType());
        return validator;
    }

    /**
     * 自定义校验器的构建，供子类覆盖
     * 
     * @param cls
     * @param field
     * @return
     */
    protected IBeanEMValidator doLoadCustomValidator(Class<?> cls, Field field) {
        return null;
    }

    /**
     * 构建解析器
     * 
     * @param cls
     * @param field
     * @return
     */
    protected IBeanEMExtractor doLoadExtractor(Class<?> cls, Field field) {
        if (field.isAnnotationPresent(BaseBeanEMExtractorAnno.class)) {
            BaseBeanEMExtractor extractor = new BaseBeanEMExtractor();
            BaseBeanEMExtractorAnno anno = field.getAnnotation(BaseBeanEMExtractorAnno.class);
            extractor.setNextStep(anno.nextStep());
            extractor.setOffsetSheet(anno.offsetSheet());
            extractor.setOffsetX(anno.offsetX());
            extractor.setOffsetY(anno.offsetY());
            extractor.setSkipOffsetSheet(anno.skipOffsetSheet());
            extractor.setSkipOffsetX(anno.skipOffsetX());
            extractor.setSkipOffsetY(anno.skipOffsetY());
            return extractor;
        } else if (field.isAnnotationPresent(MixinBeanEMExtractorAnno.class)) {
            MixinBeanEMExtractorAnno anno = field.getAnnotation(MixinBeanEMExtractorAnno.class);
            MixinBeanEMExtractor extractor = new MixinBeanEMExtractor();
            extractor.setClassName(anno.beanName());
            extractor.setClassType(anno.beanType());
            return extractor;
        }
        return doLoadCustomExtractor(cls, field);
    }

    /**
     * 自定义解析器的构建，供子类覆盖
     * 
     * @param cls
     * @param field
     * @return
     */
    protected IBeanEMExtractor doLoadCustomExtractor(Class<?> cls, Field field) {
        return null;
    }

    /**
     * 获取泛型
     * 
     * @param field
     * @return
     */
    private Class<?> getGenericType(Field field) {
        Class<?> cls = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        return cls;
    }
}
