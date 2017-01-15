package com.forms.beneform4j.excel.core.exports.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.data.loader.IDataLoader;
import com.forms.beneform4j.excel.core.exports.IExcelExporter;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.em.dynamic.IDynamicTreeEM;
import com.forms.beneform4j.excel.core.model.em.file.IFileEM;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEM;

public abstract class AbstractStreamExcelExporter implements IExcelExporter {

    @Override
    public String export(final IEM model, final Object data, String filename) {
        return exportInStream(model, filename, new OutputStreamCallback() {
            @Override
            protected void callback(OutputStream os) {
                export(model, data, os);
            }
        });
    }

    @Override
    public String export(final IEM model, final Object param, final Object data, String filename) {
        return exportInStream(model, filename, new OutputStreamCallback() {
            @Override
            protected void callback(OutputStream os) {
                export(model, param, data, os);
            }
        });
    }

    @Override
    public String export(final IEM model, final Object param, final IDataLoader loader, String filename) {
        return exportInStream(model, filename, new OutputStreamCallback() {
            @Override
            protected void callback(OutputStream os) {
                export(model, param, loader, os);
            }
        });
    }

    private String exportInStream(IEM model, String filename, OutputStreamCallback callback) {
        OutputStream os = null;
        try {
            filename = resolveFilename(model, filename);
            File file = new File(filename);
            FileUtils.forceMkdir(file.getParentFile());
            os = new BufferedOutputStream(new FileOutputStream(filename));
            callback.callback(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
