package com.forms.beneform4j.excel.core.model.loader.xml.bean;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.impl.BeanEMProxy;
import com.forms.beneform4j.excel.core.model.em.bean.impl.matcher.SheetBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 解析XML配置中的<bean-workbook><br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class BeanWorkbookParser implements IEMTopElementParser {

    private final BeanEMExtractorParser extractorParser = new BeanEMExtractorParser();

    private final BeanEMMatcherParser matcherParser = new BeanEMMatcherParser();

    private final BeanEMValidatorParser validatorParser = new BeanEMValidatorParser();

    @Override
    public void parse(IResourceEMLoadContext context, Element ele) {
        BeanEM em = parseBeanEM(ele, null, false);
        context.register(em);
    }

    private BeanEM parseBeanEM(Element element, Class<?> beanType, boolean inner) {
        BeanEM em = getBeanEM(element, beanType, inner);
        beanType = em.getBeanType();

        SheetBeanEMMatcher sheetMatcher = null;
        Map<String, Field> fields = getFields(beanType);
        Map<String, IBeanEMProperty> properties = new LinkedHashMap<String, IBeanEMProperty>();
        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String name = XmlHelper.getLocalName(ele);
                if ("property".equalsIgnoreCase(name)) {
                    String fieldName = ele.getAttribute("name");
                    if (CoreUtils.isBlank(fieldName)) {
                        Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS17);
                    } else if (null != fields && !fields.containsKey(fieldName)) {
                        Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS18, fieldName);
                    }

                    Field field = null == fields ? null : fields.get(fieldName);
                    IBeanEMProperty property = this.parseProperty(sheetMatcher, ele, fieldName, field);
                    properties.put(fieldName, property);
                }
            }
        }
        em.setProperties(properties);
        setBeanWorkbookProperties(element, em);
        return em;
    }

    private Map<String, Field> getFields(Class<?> beanType) {
        if (null == beanType || Collection.class.isAssignableFrom(beanType) || Map.class.isAssignableFrom(beanType)) {
            return null;
        }
        Map<String, Field> fields = new HashMap<String, Field>();
        for (; !Object.class.equals(beanType); beanType = beanType.getSuperclass()) {
            Field[] fields2 = beanType.getDeclaredFields();
            if (null != fields2) {
                for (Field field : fields2) {
                    String fn = field.getName();
                    if (!"serialVersionUID".equals(fn) && !fields.containsKey(fn)) {
                        fields.put(fn, field);
                    }
                }
            }
        }
        return fields;
    }

    private IBeanEMProperty parseProperty(SheetBeanEMMatcher sheetMatcher, Element element, String fieldName, Field field) {
        BeanEMProperty property = new BeanEMProperty();
        property.setName(fieldName);
        // 是否可能为集合类型
        boolean collectionAble = null == field || Collection.class.isAssignableFrom(field.getType());
        setBeanPropertyProperties(element, property, fieldName, field, collectionAble);

        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String name = XmlHelper.getLocalName(ele);
                if ("matcher".equalsIgnoreCase(name) && null == property.getMatcher()) {
                    IBeanEMMatcher matcher = matcherParser.parse(ele);
                    property.setMatcher(matcher);
                } else if ("validator".equalsIgnoreCase(name) && null == property.getValidator()) {
                    IBeanEMValidator validator = validatorParser.parse(ele);
                    property.setValidator(validator);
                } else if ("extractor".equalsIgnoreCase(name) && null == property.getExtractor()) {
                    IBeanEMExtractor extractor = extractorParser.parse(ele);
                    property.setExtractor(extractor);
                } else if ("end-matcher".equalsIgnoreCase(name) && null == property.getEndMatcher()) {
                    IBeanEMMatcher endMatcher = matcherParser.parse(ele);
                    property.setEndMatcher(endMatcher);
                } else if ("inner-workbook".equalsIgnoreCase(name) && null == property.getInnerBeanEM()) {
                    Class<?> innerCls = null;
                    if (null != field) {
                        try {
                            if (collectionAble) {
                                Type t = field.getGenericType();
                                ParameterizedType p = (ParameterizedType) t;
                                innerCls = (Class<?>) p.getActualTypeArguments()[0];
                            } else {
                                innerCls = field.getType();
                            }
                        } catch (Exception e) {
                        }
                    }
                    BeanEM inner = this.parseBeanEM(ele, innerCls, true);
                    property.setInnerBeanEM(inner);
                }
            }
        }
        return property;
    }

    private void setBeanPropertyProperties(Element ele, BeanEMProperty property, String fieldName, Field field, boolean collectionAble) {
        String type = ele.getAttribute("type");
        if (!CoreUtils.isBlank(type)) {
            Class<?> cls = null;
            if ("String".equalsIgnoreCase(type)) {
                cls = String.class;
            } else if ("List".equalsIgnoreCase(type)) {
                cls = ArrayList.class;
            } else {
                cls = CoreUtils.forName(type);
            }
            if (null != field && !field.getType().isAssignableFrom(cls)) {
                Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS19, fieldName + "<!==!>" + type);
            } else {
                property.setType(cls);
            }
        } else if (null != field) {
            property.setType(field.getType());
        }

        String desc = ele.getAttribute("desc");
        if (!CoreUtils.isBlank(desc)) {
            property.setDesc(desc);
        }

        String matcher = ele.getAttribute("matcher-ref");
        if (!CoreUtils.isBlank(matcher)) {
            IBeanEMMatcher m = matcherParser.parse(matcher);
            property.setMatcher(m);
        }

        String validator = ele.getAttribute("validator-ref");
        if (!CoreUtils.isBlank(validator)) {
            IBeanEMValidator v = validatorParser.parse(validator);
            property.setValidator(v);
        }

        String extractor = ele.getAttribute("extractor-ref");
        if (!CoreUtils.isBlank(extractor)) {
            IBeanEMExtractor e = extractorParser.parse(extractor);
            property.setExtractor(e);
        }

        String endMatcher = ele.getAttribute("endMatcher-ref");
        if (!CoreUtils.isBlank(endMatcher)) {
            IBeanEMMatcher m = matcherParser.parse(endMatcher);
            property.setEndMatcher(m);
        }

        String innerWorkbook = ele.getAttribute("innerWorkbook-ref");
        if (!CoreUtils.isBlank(innerWorkbook)) {
            IBeanEM inner = new BeanEMProxy(innerWorkbook);
            property.setInnerBeanEM(inner);
        }
    }

    private void setBeanWorkbookProperties(Element ele, BeanEM em) {
        em.setType(EMType.BEAN);

        String name = ele.getAttribute("name");
        if (!CoreUtils.isBlank(name)) {
            em.setName(name);
        }

        String desc = ele.getAttribute("desc");
        if (!CoreUtils.isBlank(desc)) {
            em.setDesc(desc);
        }

        int prior = 0;
        String priorStr = ele.getAttribute("prior");
        if (!CoreUtils.isBlank(priorStr)) {
            prior = Integer.parseInt(priorStr);
        }
        em.setPrior(prior);
    }

    private BeanEM getBeanEM(Element ele, Class<?> fieldType, boolean inner) {
        BeanEM em = new BeanEM();
        String id = ele.getAttribute(XmlEMLoaderConsts.ID_PROPERTY);
        boolean idIsEmpty = CoreUtils.isBlank(id);
        String beanType = ele.getAttribute("beanType");
        boolean beanTypeIsEmpty = CoreUtils.isBlank(beanType);

        if (!inner && idIsEmpty && beanTypeIsEmpty) {//顶层配置，id和beanType不能同时为空
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS20);
        }

        Class<?> cls = null;
        if (!beanTypeIsEmpty) {//配置了类型
            cls = CoreUtils.forName(beanType);
            if (null != fieldType && !fieldType.isAssignableFrom(cls)) {
                Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS19, beanType);
            }
        } else if (null != fieldType) {
            cls = fieldType;
        } else {
            cls = LinkedHashMap.class;
        }
        em.setBeanType(cls);

        if (idIsEmpty && !LinkedHashMap.class.equals(cls)) {
            id = cls.getName();
        }
        em.setId(id);
        return em;
    }

}
