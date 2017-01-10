package com.forms.beneform4j.excel.exports;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.data.loader.IDataLoader;
import com.forms.beneform4j.excel.model.base.IEM;

public abstract class AbstractStreamExcelExporter implements IExcelExporter {

    @Override
    public void export(IEM model, Object data, String filename) {
        OutputStream os = null;
        try {
            File file = new File(filename);
            FileUtils.forceMkdir(file.getParentFile());
            os = new BufferedOutputStream(new FileOutputStream(filename));
            this.export(model, data, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CoreUtils.closeQuietly(os);
        }
    }

    @Override
    public void export(IEM model, IDataLoader loader, Object param, String filename) {
        OutputStream os = null;
        try {
            File file = new File(filename);
            FileUtils.forceMkdir(file.getParentFile());
            os = new BufferedOutputStream(new FileOutputStream(filename));
            this.export(model, loader, param, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CoreUtils.closeQuietly(os);
        }
    }
}
