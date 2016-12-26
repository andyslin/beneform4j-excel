package com.forms.beneform4j.excel.export.builder;

import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.export.ExcelUtils;
import com.forms.beneform4j.excel.export.datastream.handler.IDataStreamHandler;
import com.forms.beneform4j.excel.export.grid.Grid;
import com.forms.beneform4j.excel.export.grid.Td;
import com.forms.beneform4j.excel.export.render.ITdRender;
import com.forms.beneform4j.excel.export.render.impl.BeanPropertyRender;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel对象生成类，并实现大数据处理器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2015-5-8<br>
 */
public class ModelExcelBuilder implements IDataStreamHandler{
	
	private static final Logger log = LoggerFactory.getLogger(ModelExcelBuilder.class);
	
	private static final ITdRender defaultRender = new BeanPropertyRender();
	
	protected void init(){
	}

	/**
	 * 大数据量处理方法
	 */
	public Workbook build(ModelExcelBuilderParam param) {
		sInit(param);	// 初始化
		Object obj = param.getWrap().handler(this);
		if(totalRows >= begin-2){
			Sheet dataSheet = wb.getSheetAt(0);//数据表单
			//设置自动筛选
			if(param.isAutoFilter()){
				dataSheet.setAutoFilter(new CellRangeAddress(begin-1,totalRows+1,0,titleColspan-1));	
			}
			//数据透视表
	 		//addPivotTable(param);
		}
		return (Workbook)obj;
	}
	
	/**
	 * 大数据量处理
	 */
	public void handler(List<Object> datas, long startIndex, int batchSeqno){
		if(0 == startIndex){
			sBuildNewWorkbook();
			sBuildNewSheet();
			totalRows = begin-1;
		}
		for(Object data : datas){
			sSetOneData(data, ++totalRows);
		}
	}
	
	/**
	 * 获取大数据量处理的结果
	 */
	public Object getResult(){
		if(null == wb){
			sBuildNewWorkbook();
			sBuildNewSheet();
		}
		return wb;
	}
	
	/**
	 * 是否需要数据透视表，子类可覆盖
	 * @param param 参数
	 * @param rows  已生成的Excel数据表单的行数，包括标题、表头等所占行数
	 * @return 返回 true 将构建数据透视表，返回false，不构建
	 */
	protected boolean needGeneratePivotTable(ModelExcelBuilderParam param, int rows){
		return param.isWritePivotTable() && rows < 1000;
	}
	
	/**
	 * 自定义设置数据透视表（比如设置行、列、数据、数据统计方法等）
	 * @param wb Excel对象
	 * @param pivotTable 数据透视表
	 * @param param 参数
	 */
	protected void customPivotTable(XSSFWorkbook wb, XSSFPivotTable pivotTable, ModelExcelBuilderParam param){
		
	}
	
	/**
	 * 创建新Excel对象
	 */
	private void sBuildNewWorkbook() {
		wb = new SXSSFWorkbook(1000);		// 创建EXCEL文件
		helper = wb.getCreationHelper();
		font = wb.createFont();
	}
	
	/**
 	 * 创建新Sheet对象
 	 */
 	private void sBuildNewSheet(){
		sheet = wb.createSheet(title);	// 创建SHEET表单
		draw = sheet.createDrawingPatriarch();
		sInitFormat(wb);				// 格式初始化
		sSetTitle();					// 设置标题
		sSetHead();						// 设置表头
 	}
 	
 	/**
	 * 设置标题
	 */
 	private void sSetTitle()
	{
		if(this.writeTitle){
			ExcelUtils.setMerge(sheet, 0, 0, TITLE_ROWS-1, titleColspan-1, title, this.titleStyle);
		}
	}
	
	/**
	 * 设置表头
	 */
 	private void sSetHead()
	{
		for(Td td : grid.getList())
		{
			if(td.isShow()){
				int beginRow = titleRowspan+td.getTop()
				  , beginCol = td.getLeft()					
				  , endRow = titleRowspan+td.getTop()+td.getRowspan()-1
				  , endCol = td.getLeft()+td.getColspan()-1;
				ExcelUtils.setMerge(sheet, beginRow, beginCol, endRow, endCol, td.getFieldName(), headStyle);
				// 添加注释
				if(null != td.getMemo() && !"".equals(td.getMemo().trim())){
					ClientAnchor anchor = getClientAnchor(0, 0, 0, 0, beginCol, beginRow, 0, 0);
					Comment comment = draw.createCellComment(anchor);
					comment.setString(getRichTextString(td.getMemo().trim()));
					CellUtil.getCell(CellUtil.getRow(beginRow, sheet), beginCol).setCellComment(comment);
				}	
			}
		}
		sSetHiddenAndLocked();
	}
	
 	private ClientAnchor getClientAnchor(int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2){
		ClientAnchor anchor =  helper.createClientAnchor();
		anchor.setDx1(dx1);
		anchor.setDy1(dy1);
		anchor.setDx2(dx2);
		anchor.setDy2(dy2);
		anchor.setCol1(col1);
		anchor.setRow1(row1);
		anchor.setCol2(col2);
		anchor.setRow2(row2);
		return anchor;
	}
	
 	private RichTextString getRichTextString(String text){
		RichTextString rts = helper.createRichTextString(text);
		rts.applyFont(font); 	//用于解决2007格式中批注显示不出来的问题
		return rts;
	}
	
	/**
	 * 设置隐藏和锁定
	 */
 	private void sSetHiddenAndLocked(){
		List<Td> leaf = grid.getLeaf();
		for(int i=0,s=leaf.size(); i<s; i++){
			if(leaf.get(i).isHidden()){
				sheet.setColumnHidden(i, true);	
			}
		}
		sheet.createFreezePane(grid.getLockedIndex(), begin);
		ExcelUtils.autoSizeColumn(sheet, titleColspan);
	}
 	
 	/**
 	 * 初始化，只需调用一次
 	 * @param grid
 	 */
 	private void sInit(ModelExcelBuilderParam param){
 		this.grid = param.getGrid();
 		this.leaf = grid.getLeaf();
		this.title = param.getTitle();// 主标题
		this.writeTitle = param.isWriteTitle();
		this.titleRowspan = this.writeTitle ? TITLE_ROWS : 0;// 标题所占的行数
		this.titleColspan = grid.getColspan();			// 标题所占的列数，即表头部分所占列数
		this.begin = titleRowspan + grid.getRowspan();	// 开始写数据的行数，即标题行数加上表头行数
 	}
 	
 	/**
	 * 初始化格式（格式和wb对象相关联，每创建一个wb对象都需要调用一次）
	 * @param wb
	 */
 	private void sInitFormat(Workbook wb)
	{
		DataFormat format = wb.createDataFormat();
		this.titleStyle = ExcelUtils.getTitleStyle(wb);
		this.headStyle = ExcelUtils.getHeadStyle(wb);
		this.msgStyle = ExcelUtils.getStyle(wb, HSSFColor.BLACK.index, HSSFColor.WHITE.index, HorizontalAlignment.LEFT);
		List<Td> tds = grid.getLeaf();	//叶子节点
		this.dataStyle = new CellStyle[tds.size()];
		for(int i=0,l=tds.size(); i<l; i++)
		{
			Td td = tds.get(i);
			dataStyle[i] = ExcelUtils.getDataStyle(wb, format, td.getAlignType(), td.getDataFormat());
		}
	}
 	
 	/**
 	 * 写Excel日志
 	 * @param sheet
 	 * @param msg
 	 */
 	private void writeExcelLog(Sheet sheet, String msg){
		ExcelUtils.setMerge(sheet, 0, 0, 30, 12, msg, this.msgStyle);
	}
	
 	/**
	 * 添加数据透视表
	 * @param grid
	 * @param params
	 */
	@SuppressWarnings("unused")
	private void addPivotTable(ModelExcelBuilderParam param){
		if(!needGeneratePivotTable(param, totalRows)){
			return;
		}
		XSSFSheet pivotSheet = null;
		try{
			XSSFWorkbook workbook = null;
	 		if(wb instanceof XSSFWorkbook){
	 			workbook = (XSSFWorkbook)wb;
	 		}else if(wb instanceof SXSSFWorkbook){
	 			workbook = ((SXSSFWorkbook)wb).getXSSFWorkbook();
	 		}else{
	 			log.debug("暂不支持2003版的Excel添加数据透视表");
	 			return ;
	 		}
	 		pivotSheet = workbook.createSheet("数据透视表");
	 		if(totalRows >= 1000){
	 			writeExcelLog(pivotSheet, "数据记录数太多，不宜生成数据透视表");
	 		}
	 		String area = "A"+(titleRowspan+1)+":"+ExcelUtils.getColumnPosition(titleColspan-1)+(totalRows+1);
	 		Sheet dataSheet = wb.getSheetAt(0);//数据表单
	 		XSSFPivotTable pivotTable = pivotSheet.createPivotTable(new AreaReference(area, SpreadsheetVersion.EXCEL2007), new CellReference("A5"), dataSheet);
	 		//workbook.setActiveSheet(1);//将数据透视表设置为当前表单
	 		pivotSheet.setActiveCell(new CellAddress("A5"));
	 		//其它设置
	 		customPivotTable(workbook, pivotTable, param);
		}catch(Exception e){
			log.error("添加数据透视表出现异常，忽略该异常",e);
			if(null != pivotSheet){
				writeExcelLog(pivotSheet, Throw.getStackMessage(e));
			}
		}
 	}
	
	/**
 	 * 设置一行数据
 	 * @param data
 	 * @param rowIndex
 	 */
 	private void sSetOneData(Object data, int rowIndex)
 	{
 		for(int j=0; j<titleColspan; j++)
		{
 			Td td = leaf.get(j);
 			Object val = sGetRender(td).render(data, td, rowIndex, j);
			ExcelUtils.setMerge(sheet, rowIndex, j, rowIndex, j, val, dataStyle[j]);
		}
 	}
 	
 	private ITdRender sGetRender(Td td){
 		ITdRender render =  td.getRender();
 		return render == null ? defaultRender : render;
 	}
	
	/**
 	 * 常量，只和模型相关，一次下载只需初始化一次
 	 */
	private Grid grid = null;				// 下载模型
	private List<Td> leaf = null;
	private String title = null;			// 主标题
	private boolean writeTitle = true;		// 是否写标题
	private Sheet sheet = null;				// 表单对象
	private CellStyle titleStyle = null;	// 标题格式
 	private CellStyle headStyle = null;		// 表头格式
 	private CellStyle[] dataStyle = null;	// 数据格式
 	private CellStyle msgStyle = null;
	private int totalRows;
	
	/**
	 * 格式，和Excel对象相关联，每次创建Excel对象需要重新初始化格式
	 */
	private Workbook wb = null;			// Excel对象
	private CreationHelper helper = null;   // 帮助类
	private Drawing draw = null;			// 绘制类
	private Font font = null;				// 字体
	private int titleRowspan;				// 标题所占的行数
	private int titleColspan;				// 标题所占的列数，即表头部分所占列数
	private int begin;					// 开始写数据的行数，即标题行数加上表头行数
	
	private static final int TITLE_ROWS = 1;	
}
