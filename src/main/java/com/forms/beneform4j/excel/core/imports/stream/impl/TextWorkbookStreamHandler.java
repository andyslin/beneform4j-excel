package com.forms.beneform4j.excel.core.imports.stream.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;

import com.forms.beneform4j.core.util.CoreUtils;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 逐行处理的向文本文本表示的PrintStream输出的回调处理器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class TextWorkbookStreamHandler extends PrintStreamWorkbookStreamHandler {

    private String filename;

    private String charset;

    @Override
    public void initialize() {
        String filename = getFilename();
        if (CoreUtils.isBlank(filename)) {
            throw new IllegalArgumentException("filename is null.");
        }

        try {
            File file = new File(filename);
            FileUtils.forceMkdirParent(file);

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            String charset = getCharset();
            if (CoreUtils.isBlank(charset)) {
                setPrintStream(new PrintStream(stream, true));
            } else {
                setPrintStream(new PrintStream(stream, true, charset));
            }
            super.initialize();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void end() {
        CoreUtils.closeQuietly(getPrintStream());
        super.end();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
