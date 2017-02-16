package com.forms.beneform4j.excel.core.model.em;

import java.util.List;

import com.forms.beneform4j.excel.ExcelComponentConfig;
import com.forms.beneform4j.excel.core.model.loader.IEMLoader;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Excel模型管理类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class EMManager {

    /**
     * 加载Excel模型
     * 
     * @param modelId 模型ID
     * @return Excel模型
     */
    public static IEM load(String modelId) {
        List<IEMLoader> loaders = ExcelComponentConfig.getEmLoaders();
        if (null != loaders) {
            for (IEMLoader loader : loaders) {
                IEM model = loader.load(modelId);
                if (null != model) {
                    return model;
                }
            }
        }
        return null;
    }

    /**
     * 移除Excel模型
     * 
     * @param modelId 模型ID
     */
    public static void remove(String modelId) {
        List<IEMLoader> loaders = ExcelComponentConfig.getEmLoaders();
        if (null != loaders) {
            for (IEMLoader loader : loaders) {
                loader.remove(modelId);
            }
        }
    }

    /**
     * 从指定加载器中移除Excel模型
     * 
     * @param loader 模型加载器
     * @param modelId 模型ID
     */
    public static void remove(IEMLoader loader, String modelId) {
        if (null != loader && null != modelId) {
            loader.remove(modelId);
        }
    }

    /**
     * 清除Excel模型
     */
    public static void clear() {
        List<IEMLoader> loaders = ExcelComponentConfig.getEmLoaders();
        if (null != loaders) {
            for (IEMLoader loader : loaders) {
                loader.clear();
            }
        }
    }

    /**
     * 清除指定加载器中的Excel模型
     * 
     * @param loader 模型加载器
     */
    public static void clear(IEMLoader loader) {
        if (null != loader) {
            loader.clear();
        }
    }
}
