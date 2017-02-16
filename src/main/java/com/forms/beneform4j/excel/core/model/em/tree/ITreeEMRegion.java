package com.forms.beneform4j.excel.core.model.em.tree;

import java.io.Serializable;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 树型配置模型的区域配置接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface ITreeEMRegion extends Serializable {

    /**
     * 偏移基准点
     */
    public enum OffsetPoint {
        /**
         * 左上角顶点
         */
        LEFT_TOP,
        /**
         * 右上角顶点
         */
        RIGHT_TOP,
        /**
         * 右下角顶点
         */
        RIGHT_BUTTOM,
        /**
         * 左下角顶点(默认)
         */
        LEFT_BUTTOM;
    }

    /**
     * 获取所属的Excel模型
     * 
     * @return
     */
    public ITreeEM getWorkbook();

    /**
     * 获取所属的表单模型
     * 
     * @return
     */
    public ITreeEMSheet getSheet();

    /**
     * 获取区域名称
     * 
     * @return
     */
    public String getName();

    /**
     * 获取区域标题
     * 
     * @return
     */
    public String getTitle();

    /**
     * 获取偏移的基准区域名称，默认为上一个同一级别的区域
     * 
     * @return
     */
    public String getOffsetName();

    /**
     * 获取偏移的基准区域点，默认为左下角顶点
     * 
     * @return
     */
    public OffsetPoint getOffsetPoint();

    /**
     * 获取横向偏移量
     * 
     * @return
     */
    public int getOffsetX();

    /**
     * 获取纵向偏移量
     * 
     * @return
     */
    public int getOffsetY();

    /**
     * 获取生成区域的条件
     * 
     * @return
     */
    public String getCondition();

    /**
     * 获取数据的表达式
     * 
     * @return
     */
    public String getExpression();

    /**
     * 获取区域上的组件
     * 
     * @return
     */
    public ITreeEMComponent getComponent();
}
