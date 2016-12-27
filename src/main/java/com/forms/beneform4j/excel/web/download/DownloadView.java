package com.forms.beneform4j.excel.web.download;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

import com.forms.beneform4j.core.dao.stream.IListStreamReader;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.export.datastream.handler.IDataStreamHandler;
import com.forms.beneform4j.excel.export.datastream.wrap.IDataStreamHandlerWrap;
import com.forms.beneform4j.excel.export.grid.Grid;
import com.forms.beneform4j.excel.export.grid.GridFactory;
import com.forms.beneform4j.excel.web.builder.BuilderManager;
import com.forms.beneform4j.excel.web.builder.IDataObjectBuilder;
import com.forms.beneform4j.excel.web.builder.IObjectBuilder;
import com.forms.beneform4j.security.core.login.user.IUser;
import com.forms.beneform4j.util.Tool;
import com.forms.beneform4j.web.ajax.AjaxInvoker;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 下载视图<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-6<br>
 */
public class DownloadView extends AbstractView {

    /**
     * 为了给下载增加进度条，整个下载处于Ajax请求中，这里的AJAX_ID则表示这个Ajax请求的ID
     */
    private final static String AJAX_ID = "ajaxStatusId";
    /**
     * 下载文件时，是在浏览器在线显示，还是下载对话框，默认为下载对话框 inline内嵌在浏览器中 attachment弹出下载对话框
     */
    private final static String SHOW_TYPE = "showType";
    /**
     * 下载文件的后缀
     */
    private final static String SUFFIX = "downloadSuffix";
    /**
     * 下载类型
     * 
     * @see DownloadType
     */
    private final static String DOWNLOAD_TYPE = "downloadType";
    /**
     * 下载类型为先构建对象时的构建类型
     */
    private final static String BUILD_TYPE = "buildType";
    /**
     * 下载类型为导出数据时的下载模型ID
     */
    private final static String ID = "downloadId";
    /**
     * 下载类型为导出数据时的下载标题
     */
    private final static String TITLE = "downloadTitle";

    protected boolean generatesDownloadContent() {
        return true;
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File local = null;
        ByteArrayOutputStream os = null;
        ServletOutputStream out = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        String ajaxStatusId = getParameter(request, AJAX_ID);
        String showType = getParameter(request, SHOW_TYPE, "attachment");
        boolean flag = false;
        try {
            IUser user = getUser(request);
            if (null != user) {
                CommonLogger.info("下载:用户代码【" + user.getUserId() + "】用户名称【" + user.getUserName() + "】日期【" + Tool.DATE.getDateAndTime() + "】");
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            out = response.getOutputStream();

            String name = getParameter(request, TITLE);
            String suffix = getParameter(request, SUFFIX, "xlsx").toLowerCase();// 文件后缀

            DownloadType type = DownloadType.instance(getParameter(request, DOWNLOAD_TYPE));
            String downloadId = getDownloadIdOrBuildType(request);// 下载模型ID或构建类型
            Object result = null;
            if (DownloadType.OBJECT.equals(type)) {
                IObjectBuilder builder = BuilderManager.getObjectBuilder(downloadId);
                result = builder.build(request, model, suffix);
            } else {
                IDataObjectBuilder builder = BuilderManager.getDataObjectBuilder(suffix);
                Grid grid = getGrid(request, downloadId, type);// 下载模型
                if (null == name) {
                    name = grid.getModelName();
                }
                IDataStreamHandlerWrap wrap = getDataStreamHandlerWrap(type);
                result = builder.buildDataObject(request, grid, wrap, model);
            }

            if (result instanceof File)// 如果返回的是文件
            {
                local = (File) result;
                String filename = local.getName();
                response.addHeader("Content-Disposition", showType + ";filename=" + new String(filename.getBytes("gb2312"), "iso-8859-1"));
                fis = new FileInputStream(local);
                bis = new BufferedInputStream(fis);
                Tool.IO.copy(bis, out);
            } else if (result instanceof Workbook)// 返回的是Excel对象
            {
                try {
                    String filename = name + "." + suffix;
                    response.addHeader("Content-Disposition", showType + ";filename=" + new String(filename.getBytes("gb2312"), "iso-8859-1"));
                    ((Workbook) result).write(out);
                } catch (Throwable e) {
                    throw Throw.createRuntimeException(e);
                } finally {
                    if (result instanceof SXSSFWorkbook) {
                        ((SXSSFWorkbook) result).dispose();
                    }
                }
            } else if (result instanceof ByteArrayOutputStream) {
                String filename = name + "." + suffix;
                response.addHeader("Content-Disposition", showType + ";filename=" + new String(filename.getBytes("gb2312"), "iso-8859-1"));
                os = (ByteArrayOutputStream) result;
                response.setContentLength(os.size());
                os.writeTo(out);
                out.flush();
            } else if (result instanceof CharSequence) {
                this.deal(response, showType, result.toString(), os, out);
            } else {
                // 其它，暂不实现
            }
            flag = true;
        } catch (OutOfMemoryError me) {
            flag = false;
            out.print("下载数据量过大，内存溢出！");
            me.printStackTrace();
            throw me;
        } catch (Exception se) {
            String c = "org.apache.catalina.connector.ClientAbortException";
            if (c.equals(se.getClass().getName()) || (null != se.getCause() && c.equals(se.getCause().getClass().getName()))) {// 客户取消下载
                flag = true;
            } else {
                try {
                    this.deal(response, showType, "下载出现异常：" + Throw.getMessage(se), os, out);
                    se.printStackTrace();
                    flag = true;
                } catch (Exception ii) {
                }
            }
        } finally {
            Tool.IO.closeQuietly(out, bis, fis, os);
            Tool.FILE.forceDelete(local);
            if (flag) {
                AjaxInvoker.updateSuccess(ajaxStatusId);
            } else {
                AjaxInvoker.updateFailure(ajaxStatusId);
            }
        }
    }

    protected IUser getUser(HttpServletRequest request) {
        return null;
    }

    protected Grid getGrid(HttpServletRequest request, String downloadId, DownloadType type) {
        return GridFactory.getGrid(downloadId);
    }

    /**
     * 获取流式数据处理器
     * 
     * @param type
     * @return
     */
    private IDataStreamHandlerWrap getDataStreamHandlerWrap(DownloadType type) {
        if (DownloadType.MODEL.equals(type)) {
            IDataStreamHandlerWrap wrap = new IDataStreamHandlerWrap() {
                @SuppressWarnings({"rawtypes", "unchecked"})
                @Override
                public Object handler(IDataStreamHandler handler) {
                    List list = new ArrayList();
                    Map<String, Object> data = new HashMap<String, Object>();
                    for (int i = 0; i < 10; i++) {// 填充10行空数据
                        list.add(data);
                    }
                    handler.handler(list, 0, 1);
                    return handler.getResult();
                }
            };
            return wrap;
        } else {
            final IListStreamReader<?> reader = DownloadExecutor.getDataStreamReader();
            if (null == reader) {
                Throw.throwRuntimeException("没有找到流式数据处理器，请检查下载URL是否正确");
            } else {
                DownloadExecutor.clearDataStreamReader();
            }
            IDataStreamHandlerWrap wrap = new IDataStreamHandlerWrap() {
                @SuppressWarnings({"rawtypes", "unchecked"})
                @Override
                public Object handler(IDataStreamHandler handler) {
                    List list = null;
                    int index = 0;
                    int batch = 1;
                    while ((list = reader.read()) != null) {
                        handler.handler(list, index, batch);
                        index += list.size();
                        batch++;
                    }
                    return handler.getResult();
                }
            };
            return wrap;
        }
    }

    /**
     * 处理字符串形式的下载
     * 
     * @param response
     * @param showType
     * @param result
     * @param os
     * @param out
     * @throws IOException
     */
    private void deal(HttpServletResponse response, String showType, String result, ByteArrayOutputStream os, ServletOutputStream out) throws IOException {
        String filename = "download.txt";
        response.addHeader("Content-Disposition", showType + ";filename=" + new String(filename.getBytes("gb2312"), "iso-8859-1"));
        os = new ByteArrayOutputStream();
        os.write(result.getBytes());
        response.setContentLength(os.size());
        os.writeTo(out);
        out.flush();
    }

    /**
     * 获取下载模型ID或构建类型
     * 
     * @param request
     * @return
     */
    private String getDownloadIdOrBuildType(HttpServletRequest request) {
        String id = getParameter(request, BUILD_TYPE);
        if (Tool.CHECK.isEmpty(id)) {
            id = getParameter(request, ID);
        }
        return id;
    }

    /**
     * 从请求中获取参数
     * 
     * @param request
     * @param name
     * @return
     */
    private String getParameter(HttpServletRequest request, String name) {
        return getParameter(request, name, null);
    }

    /**
     * 从请求中获取参数
     * 
     * @param request
     * @param name
     * @param defaultValue
     * @return
     */
    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (!Tool.CHECK.isBlank(value)) {
            return value;
        }
        return defaultValue;
    }
}
