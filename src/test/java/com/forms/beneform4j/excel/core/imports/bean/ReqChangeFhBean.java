package com.forms.beneform4j.excel.core.imports.bean;

import java.io.Serializable;
import java.util.List;

import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult.NextStep;
import com.forms.beneform4j.excel.core.model.loader.anno.BeanEMNestedFieldAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.extractor.BaseBeanEMExtractorAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell.BaseBeanEMMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell.MixinBeanEMMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.cell.PositionBeanEMMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.matcher.end.BaseBeanEMEndMatcherAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.validator.BaseBeanEMValidatorAnno;
import com.forms.beneform4j.excel.core.model.loader.anno.validator.CompositeBeanEMValidatorAnno;

public class ReqChangeFhBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6223558777150725207L;

    /**
     * 校验
     */
    @CompositeBeanEMValidatorAnno({@BaseBeanEMValidatorAnno(offsetX = 1, value = "产品简称"), //
            @BaseBeanEMValidatorAnno(offsetX = 2, value = "版本情况"), //
            @BaseBeanEMValidatorAnno(offsetX = 3, value = "工作量（人天）", // 
                    isContinue = true)})
    @BaseBeanEMMatcherAnno(value = "工作量说明")
    private Object validators;

    /**
     * 标题 使用绝对位置定位，然后解析定位到的Cell，解析完后继续下一行的循环
     */
    @PositionBeanEMMatcherAnno(rowPosition = 1, cellPosition = "A")
    @BaseBeanEMExtractorAnno(nextStep = NextStep.CONTINUE_ROW)
    private String title;

    /**
     * 需求变更名称 使用相对位置定位，然后解析已定位好的Cell的在X方向偏移1个位置的Cell，解析完后继续下一行的循环
     */
    @BaseBeanEMMatcherAnno(value = "需求变更名称")
    @BaseBeanEMExtractorAnno(offsetX = 1, nextStep = NextStep.CONTINUE_ROW)
    private String reqChangeName;

    /**
     * 软件中心需求编号
     */
    @BaseBeanEMMatcherAnno(value = "软件中心需求编号")
    @BaseBeanEMExtractorAnno(offsetX = 1, nextStep = NextStep.CONTINUE_ROW)
    private String centerReqNo;

    /**
     * 软件中心负责人及电话 使用相对位置定位，然后解析已定位好的Cell的在X方向偏移1个位置的Cell，解析完后跳过1列，继续处理
     */
    @BaseBeanEMMatcherAnno(value = "软件中心负责人及电话")
    @BaseBeanEMExtractorAnno(offsetX = 1, skipOffsetX = 1)
    private String centerPrincipalAndTel;

    /**
     * 分行负责人及电话 使用相对位置定位，然后解析已定位好的Cell的在X方向偏移1个位置的Cell，解析完后跳过1行，继续下一行的循环
     */
    @BaseBeanEMMatcherAnno(value = "分行负责人及电话")
    @BaseBeanEMExtractorAnno(offsetX = 1, skipOffsetY = 1, nextStep = NextStep.CONTINUE_ROW)
    private String fhPrincipalAndTel;

    @BeanEMNestedFieldAnno
    @BaseBeanEMMatcherAnno(pattern = "涉及改造产品\\s*（总行）", offsetX = 1)
    private Production production;

    /**
     * 总行资源、部署及系统环境变化
     */
    @BaseBeanEMMatcherAnno(value = "总行资源、部署及系统环境变化", offsetX = 1)
    private String envChangeOfChief;

    /**
     * 总行接口压力影响
     */
    @BaseBeanEMMatcherAnno(value = "总行接口压力影响", offsetX = 1)
    private String pressAffectOfChief;

    /**
     * 系统安全性（符合情况）
     */
    @BaseBeanEMMatcherAnno(pattern = "系统安全性\\s*（符合情况）", offsetX = 1)
    private String systemSecurity;

    /**
     * 改造方案说明（总分行整体说明）（复杂方案说明需另行附件）
     */
    @BaseBeanEMMatcherAnno(pattern = "改造方案说明\\s*（总分行整体说明）", offsetX = 1)
    private String schemeIntroduce;

    /**
     * 新增接口
     */
    @BaseBeanEMMatcherAnno(value = "新增接口", offsetX = 1)
    private String addInterface;

    /**
     * 已开放接口
     */
    @BaseBeanEMMatcherAnno(value = "已开放接口", offsetX = 1)
    private String openedInterface;

    /**
     * 实施步骤 1.定位每个循环体开始的位置 2.定位整个循环结束的位置，如果没有定义，将一直执行直到当前sheet处理完
     */
    @BaseBeanEMMatcherAnno(value = "总工作量（人天）")
    @BaseBeanEMEndMatcherAnno(@BaseBeanEMMatcherAnno(value = "其他说明"))
    private List<Step> steps;

    /**
     * 其他说明 解析完后直接结束处理
     */
    @BaseBeanEMMatcherAnno(value = "其他说明", offsetX = 1)
    @BaseBeanEMExtractorAnno(nextStep = NextStep.END)
    private String otherIntroduce;

    /**
     * 对象属性测试
     */
    public static class Production implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -205934213106640729L;

        /**
         * 涉及改造产品（总行） 使用正则表达式定位，目标位置的左边一个单元格匹配正则表达式，然后使用默认解析器解析
         */
        @BaseBeanEMMatcherAnno(pattern = "涉及改造产品\\s*（总行）", offsetX = 1)
        private String involveChangeProduceOfChief;

        /**
         * 涉及改造产品（分行）
         */
        @BaseBeanEMMatcherAnno(pattern = "涉及改造产品\\s*（分行）", offsetX = 1)
        private String involveChangeProduceOfBranch;

        /**
         * 配合测试产品（总行）
         */
        @BaseBeanEMMatcherAnno(value = "配合测试产品（总行）", offsetX = 1)
        private String coopTestProduceOfChief;
    }

    /**
     * 列表属性测试
     */
    public static class Step implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 2364865259822709391L;

        /**
         * 总工作量（人天）
         */
        @BaseBeanEMMatcherAnno(value = "总工作量（人天）", offsetX = 1)
        private double totalLoad;

        /**
         * 实施周期(工作日)
         */
        @BaseBeanEMMatcherAnno(value = "实施周期(工作日)", offsetX = 1)
        private String operatePeriod;

        /**
         * 实施建议
         */
        @BaseBeanEMMatcherAnno(value = "实施建议", offsetX = 1)
        private String operateAdvise;

        /**
         * 工作量说明 使用自定义的匹配器匹配每个循环体的开始位置
         */
        @MixinBeanEMMatcherAnno(beanType = LoadDetailsMatcher.class)
        @BaseBeanEMEndMatcherAnno(@BaseBeanEMMatcherAnno(pattern = "[(其他说明)|(总工作量（人天）)]"))
        private List<LoadDetail> loadDetails;
    }

    /**
     * 嵌套列表属性测试
     */
    public static class LoadDetail implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -604928468347657737L;

        /**
         * 产品简称 匹配当前行的B位置处
         */
        @PositionBeanEMMatcherAnno(cellPosition = "B")
        private String produceShortName;

        /**
         * 版本情况
         */
        @PositionBeanEMMatcherAnno(cellPosition = "C")
        private String version;

        /**
         * 工作量（人天）
         */
        @PositionBeanEMMatcherAnno(cellPosition = "D")
        private double load;
    }
}
