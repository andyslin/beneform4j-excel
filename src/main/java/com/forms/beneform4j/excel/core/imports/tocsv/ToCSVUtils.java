package com.forms.beneform4j.excel.core.imports.tocsv;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PushbackInputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;

public class ToCSVUtils {

    private enum ExcelVersion {
        V_2003, V_2007;
    }

    /**
     * 按默认转为CSV
     * 
     * @param excel
     * @param csv
     */
    public static void xls2csv(String excel, String csv) {
        xls2csv(excel, csv, -1, null, null, -1);
    }

    /**
     * 将Excel转为CSV
     * 
     * @param excel
     * @param csv
     * @param separator
     */
    public static void xls2csv(String excel, String csv, String separator) {
        xls2csv(excel, csv, -1, null, separator, -1);
    }

    /**
     * 将Excel转为CSV
     * 
     * @param excel
     * @param csv
     * @param charset
     * @param separator
     */
    public static void xls2csv(String excel, String csv, String charset, String separator) {
        xls2csv(excel, csv, -1, charset, separator, -1);
    }

    /**
     * 将Excel转为CSV
     * 
     * @param excel Excel文件
     * @param csv CSV文件
     * @param cols 转换的列数不足时补分隔符，列数多时不减，默认时按Excel的本来列数输出
     * @param charset 输出字符集，默认为UTF-8
     * @param separator 分隔符，默认为逗号,
     * @param sheetNum 需转换的Sheet个数，默认为全部转换
     */
    public static void xls2csv(String excel, String csv, int cols, String charset, String separator, int sheetNum) {
        OutputStream output = null;
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(excel));
            output = new BufferedOutputStream(new FileOutputStream(csv));
            xls2csv(input, output, cols, charset, separator, sheetNum);
        } catch (IOException ie) {
            Throw.throwRuntimeException("文件读写异常", ie);
        } finally {
            CoreUtils.closeQuietly(output, input);
        }
    }

    /**
     * 按默认转为CSV
     * 
     * @param input Excel文件输入流
     * @param output CSV文件输出流
     */
    public static void xls2csv(InputStream input, OutputStream output) {
        xls2csv(input, output, -1, null, null, -1);
    }

    /**
     * 将Excel转为CSV
     * 
     * @param input Excel文件输入流
     * @param output CSV文件输出流
     * @param separator
     */
    public static void xls2csv(InputStream input, OutputStream output, String separator) {
        xls2csv(input, output, -1, null, separator, -1);
    }

    /**
     * 将Excel转为CSV
     * 
     * @param input Excel文件输入流
     * @param output CSV文件输出流
     * @param charset
     * @param separator
     */
    public static void xls2csv(InputStream input, OutputStream output, String charset, String separator) {
        xls2csv(input, output, -1, charset, separator, -1);
    }

    /**
     * 将Excel转为CSV
     * 
     * @param input Excel文件输入流
     * @param output CSV文件输出流
     * @param cols 转换的列数不足时补分隔符，列数多时不减，默认时按Excel的本来列数输出
     * @param charset 输出字符集，默认为UTF-8
     * @param separator 分隔符，默认为逗号,
     * @param sheetNum 需转换的Sheet个数，默认为全部转换
     */
    public static void xls2csv(InputStream input, OutputStream output, int cols, String charset, String separator, int sheetNum) {
        PrintStream ps = null;
        try {
            if (CoreUtils.isBlank(charset)) {
                charset = "UTF-8";
            }
            if (CoreUtils.isBlank(separator)) {
                separator = ",";
            }

            ps = new PrintStream(output, true, charset);
            ExcelVersion version = autoExcelVersion(input);
            if (ExcelVersion.V_2003.equals(version)) {
                XLS2CSVmra xls2csv = new XLS2CSVmra(new POIFSFileSystem(input), ps, cols, separator, sheetNum);
                xls2csv.process();
            } else {
                XLSX2CSV xlsx2csv = new XLSX2CSV(OPCPackage.open(input), ps, cols, separator, sheetNum);
                xlsx2csv.process();
            }
        } catch (IOException ie) {
            Throw.throwRuntimeException("文件读写异常", ie);
        } catch (Exception e) {
            Throw.throwRuntimeException("将Excel文件转为csv出现异常", e);
        } finally {
            CoreUtils.closeQuietly(ps);
        }
    }

    /**
     * 侦测Excel文件的版本
     * 
     * @param inp
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    private static ExcelVersion autoExcelVersion(InputStream inp) throws IOException, InvalidFormatException {
        // If clearly doesn't do mark/reset, wrap up
        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }

        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
            return ExcelVersion.V_2003;
        }
        if (DocumentFactoryHelper.hasOOXMLHeader(inp)) {
            return ExcelVersion.V_2007;
        }
        throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }
}
