package com.forms.beneform4j.excel.core.exports.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.core.model.em.file.IFileEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的Excel导出实现类，将输出文件名转换为输出流，并自动修正文件名的后缀<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public abstract class AbstractOutputStreamExcelExporter extends AbstractModelExcelExporter {

    @Override
    public String exports(final IEM model, final Object data, String filename) {
        return exportsWithOutputStream(model, filename, new OutputStreamCallback() {
            @Override
            protected void callback(OutputStream os) {
                exports(model, data, os);
            }
        });
    }

    @Override
    public String exports(final IEM model, final Object param, final Object data, String filename) {
        return exportsWithOutputStream(model, filename, new OutputStreamCallback() {
            @Override
            protected void callback(OutputStream os) {
                exports(model, param, data, os);
            }
        });
    }

    private String exportsWithOutputStream(IEM model, String filename, OutputStreamCallback callback) {
        OutputStream os = null;
        try {
            filename = resolveFilename(model, filename);
            File file = new File(filename);
            FileUtils.forceMkdir(file.getParentFile());
            os = new BufferedOutputStream(new FileOutputStream(filename));
            callback.callback(os);
        } catch (IOException e) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS02, e, filename);
        } finally {
            CoreUtils.closeQuietly(os);
        }
        return filename;
    }

    private String resolveFilename(IEM model, String filename) {
        if (model instanceof IFileEM) {
            try {
                String s = FilenameUtils.getExtension(((IFileEM) model).getFilename());//模板文件后缀
                String ts = FilenameUtils.getExtension(filename);//目标文件后缀
                if (!CoreUtils.isBlank(s)) {
                    if (CoreUtils.isBlank(ts)) {
                        filename = filename + "." + s;
                    } else {
                        filename = filename.substring(0, filename.lastIndexOf(".")) + "." + s;
                    }
                } else if (CoreUtils.isBlank(ts)) {//模板文件和目标文件后缀都为空，直接添加xlsx后缀
                    filename = filename + ".xlsx";
                }
            } catch (Exception e) {
                // ignore
            }
        } else if (model instanceof ITreeEM || model instanceof IDynamicTreeEM) {
            String ts = FilenameUtils.getExtension(filename);//目标文件后缀
            if (CoreUtils.isBlank(ts)) {
                filename = filename + ".xlsx";
            } else {
                filename = filename.substring(0, filename.lastIndexOf(".")) + ".xlsx";
            }
        }
        return filename;
    }

    private abstract class OutputStreamCallback {
        abstract protected void callback(OutputStream os);
    }
}
