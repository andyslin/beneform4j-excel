package com.forms.beneform4j.excel.exports.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.data.loader.IDataLoader;
import com.forms.beneform4j.excel.exports.IExcelExporter;
import com.forms.beneform4j.excel.model.base.IEM;

public abstract class AbstractStreamExcelExporter implements IExcelExporter {

    @Override
    public void export(final IEM model, final Object data, String filename) {
        exportInStream(filename, new OutputStreamCallback() {
            @Override
            protected void callback(OutputStream os) {
                export(model, data, os);
            }
        });
    }

    @Override
    public void export(final IEM model, final Object param, final Object data, String filename) {
        exportInStream(filename, new OutputStreamCallback() {
            @Override
            protected void callback(OutputStream os) {
                export(model, param, data, os);
            }
        });
    }

    @Override
    public void export(final IEM model, final Object param, final IDataLoader loader, String filename) {
        exportInStream(filename, new OutputStreamCallback() {
            @Override
            protected void callback(OutputStream os) {
                export(model, param, loader, os);
            }
        });
    }

    private void exportInStream(String filename, OutputStreamCallback callback) {
        OutputStream os = null;
        try {
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
    }

    private abstract class OutputStreamCallback {
        abstract protected void callback(OutputStream os);
    }
}
