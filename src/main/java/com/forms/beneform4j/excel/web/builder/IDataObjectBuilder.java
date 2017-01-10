package com.forms.beneform4j.excel.web.builder;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.forms.beneform4j.excel.export.datastream.wrap.IDataStreamHandlerWrap;
import com.forms.beneform4j.excel.model.tree.component.grid.Grid;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 数据对象生成器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-6<br>
 */
public interface IDataObjectBuilder {

    /**
     * 创建数据文件对象
     * 
     * @param request 请求对象
     * @param grid 表格模型
     * @param wrap 流式数据处理包装器
     * @param model 控制层返回的数据模型
     * @return
     */
    public Object buildDataObject(HttpServletRequest request, Grid grid, IDataStreamHandlerWrap wrap, Map<String, Object> model);
}
