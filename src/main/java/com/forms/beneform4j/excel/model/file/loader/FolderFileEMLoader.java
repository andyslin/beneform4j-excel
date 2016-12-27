package com.forms.beneform4j.excel.model.file.loader;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.model.base.loader.AbstractOnceEMLoader;
import com.forms.beneform4j.excel.model.file.em.ResourceFileEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 扫描指定目录下的文件作为模型，并将目录到该文件的相对路径作为模型ID<br>
 * 
 * <pre>
 *    例如：基本目录配置为 C:/excel-model，则下面的Excel文件模型读取为：<br>
 *    1. C:/excel-model/model1.xls ---> id == model1
 *    2. C:/excel-model/subfolder/model2.xlsx --> id == subfolder/model2
 * </pre>
 * 
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public class FolderFileEMLoader extends AbstractOnceEMLoader {

    private static final Set<String> defaultSuffixs = new HashSet<String>(Arrays.asList("xls", "xlsx"));

    /**
     * 根目录
     */
    private String[] folders;

    /**
     * 后缀
     */
    private Set<String> suffixs;

    /**
     * 注入目录文件夹
     * 
     * @param folders
     */
    public void setFolders(String[] folders) {
        this.folders = folders;
    }

    /**
     * 注入后缀
     * 
     * @param suffixs
     */
    public void setSuffixs(Set<String> suffixs) {
        if (null != suffixs) {
            Set<String> ssuffixs = new HashSet<String>();
            for (String suffix : suffixs) {
                ssuffixs.add(suffix.toLowerCase());
            }
            this.suffixs = ssuffixs;
        }
    }

    @Override
    protected void init(boolean force) {
        if (null == folders || 0 == folders.length) {
            return;
        }
        Set<String> filenames = new HashSet<String>();
        Set<String> ssuffixs = null == suffixs ? defaultSuffixs : suffixs;
        for (String folder : folders) {
            doRegisterOneFolder(new File(folder), filenames, ssuffixs, null, force);
        }
    }

    private void doRegisterOneFolder(File file, Set<String> filenames, Set<String> suffixs, String parent, boolean force) {
        if (file.isDirectory()) {// 目录
            File[] files = file.listFiles();
            if (null != files && 0 != files.length) {
                if (null == parent) {
                    parent = "";
                } else {
                    parent = parent + "/" + file.getName();
                }
                for (File sub : files) {
                    doRegisterOneFolder(sub, filenames, suffixs, parent, force);
                }
            }
        } else if (file.isFile()) {// 文件
            String filename = file.getPath();
            String suffix = FilenameUtils.getExtension(filename).toLowerCase();
            if (!suffixs.contains(suffix)) {
                return;
            }

            if (filenames.contains(filename)) {// 如果已经包括了该文件，则不做处理
                CommonLogger.info("has included the file [" + filename + "]");
            } else {
                filenames.add(filename);
                Resource resource = new FileSystemResource(file);
                ResourceFileEM em = new ResourceFileEM(resource);
                String basename = FilenameUtils.getBaseName(filename);
                String id = (null == parent) ? basename : (parent + "/" + basename);
                if (id.startsWith("/")) {
                    id = id.substring(1);
                }
                em.setId(id);
                em.setName(basename);
                em.setDesc(filename);
                addEM(em, force);
            }
        }
    }
}
