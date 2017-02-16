package com.forms.beneform4j.excel.core.expansion.jett;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.RichTextString;

import com.forms.beneform4j.core.dao.stream.EmptyListStreamReader;
import com.forms.beneform4j.core.dao.stream.IListStreamReader;

import net.sf.jett.exception.TagParseException;
import net.sf.jett.expression.Expression;
import net.sf.jett.model.Block;
import net.sf.jett.tag.BaseLoopTag;
import net.sf.jett.tag.TagContext;
import net.sf.jett.util.AttributeUtil;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Beneform4j扩展的jett循环标签<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class IteratorTag extends BaseLoopTag {

    private static final boolean DEBUG = false;

    private static final String ATTR_ITEMS = "items";

    private static final String ATTR_VAR = "var";

    private static final String ATTR_INDEXVAR = "indexVar";

    private static final List<String> REQ_ATTRS = new ArrayList<String>(Arrays.asList(ATTR_ITEMS, ATTR_VAR));
    private static final List<String> OPT_ATTRS = new ArrayList<String>(Arrays.asList(ATTR_INDEXVAR));

    private IListStreamReader<Object> streamReader = null;
    private int size;
    private String myIteratorName = null;
    private String myVarName = null;
    private String myIndexVarName = null;

    @Override
    public String getName() {
        return "iterator";
    }

    @Override
    public List<String> getRequiredAttributes() {
        List<String> reqAttrs = new ArrayList<String>(super.getRequiredAttributes());
        reqAttrs.addAll(REQ_ATTRS);
        return reqAttrs;
    }

    @Override
    public List<String> getOptionalAttributes() {
        List<String> optAttrs = new ArrayList<String>(super.getOptionalAttributes());
        optAttrs.addAll(OPT_ATTRS);
        return optAttrs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validateAttributes() throws TagParseException {
        super.validateAttributes();
        if (isBodiless())
            throw new TagParseException("ForEach tags must have a body.  Bodiless ForEach tag found" + getLocation());

        TagContext context = getContext();
        Map<String, Object> beans = context.getBeans();

        Map<String, RichTextString> attributes = getAttributes();

        streamReader = AttributeUtil.evaluateObject(this, attributes.get(ATTR_ITEMS), beans, ATTR_ITEMS, IListStreamReader.class, new EmptyListStreamReader<Object>());
        streamReader.read();
        size = streamReader.size();

        String attrItems = attributes.get(ATTR_ITEMS).getString();
        int beginExprIdx = attrItems.indexOf(Expression.BEGIN_EXPR);
        int endExprIdx = attrItems.indexOf(Expression.END_EXPR);
        if (beginExprIdx != -1 && endExprIdx != -1 && endExprIdx > beginExprIdx) {
            myIteratorName = attrItems.substring(beginExprIdx + Expression.BEGIN_EXPR.length(), endExprIdx);
        }

        myVarName = AttributeUtil.evaluateString(this, attributes.get(ATTR_VAR), beans, null);

        myIndexVarName = AttributeUtil.evaluateString(this, attributes.get(ATTR_INDEXVAR), beans, null);
    }

    @Override
    protected List<String> getCollectionNames() {
        return Arrays.asList(myIteratorName);
    }

    @Override
    protected List<String> getVarNames() {
        return Arrays.asList(myVarName);
    }

    @Override
    protected int getNumIterations() {
        return size;
    }

    @Override
    protected int getCollectionSize() {
        return size;
    }

    @Override
    protected Iterator<Object> getLoopIterator() {
        return streamReader.iterator();
    }

    @Override
    protected void beforeBlockProcessed(TagContext context, Block currBlock, Object item, int index) {
        Map<String, Object> beans = context.getBeans();
        beans.put(myVarName, item);

        if (DEBUG)
            System.err.println("IteratorTag.beforeBP: index=" + index);

        if (myIndexVarName != null && myIndexVarName.length() > 0)
            beans.put(myIndexVarName, index);
    }

    @Override
    protected void afterBlockProcessed(TagContext context, Block currBlock, Object item, int index) {
        Map<String, Object> beans = context.getBeans();
        beans.remove(myVarName);

        if (myIndexVarName != null && myIndexVarName.length() > 0)
            beans.remove(myIndexVarName);
    }
}
