<?xml version="1.0" encoding="UTF-8"?>
<excel-tree-model-config xmlns="http://www.formssi.com/schema/beneform4j/excel-tree-model"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://www.formssi.com/schema/beneform4j/excel-tree-model http://www.formssi.com/schema/beneform4j/excel-tree-model.xsd">
	
	<workbook name="动态模型，会破坏XML文件结构，需要另行指定ID">
		<region>
			<td text="简单属性值" property="simpleProperty"/>
			<td text="动态计算标题">
				<td text="${(param.currentYear-2)?c}年销售收入" property="dynaProperty1"/>
				<td text="${(param.currentYear-1)?c}年销售收入" property="dynaProperty2"/>
				<td text="${param.currentYear?c}年销售收入" property="dynaProperty3"/>
			</td>
			<td text="链式属性值" property="nestProperty.innerProperty"/>
			<#list param.columns>
			<td text="动态生成列">
			    <#items as column>
			      <td text="${column.title}" property="listProperty[${column?index}].listInnerProperty"/>
			    </#items>
			</td>
			</#list>
		</region>
	</workbook>
</excel-tree-model-config>
