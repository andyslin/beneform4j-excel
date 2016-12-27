package com.forms.beneform4j.excel.export.grid;

import java.util.Arrays;
import java.util.List;

import com.forms.beneform4j.excel.export.grid.loader.IGridModelLoader;
import com.forms.beneform4j.excel.export.grid.loader.impl.XmlGridModelLoader;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 模型工厂类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-6<br>
 */
public class GridFactory {

    private static List<? extends IGridModelLoader> gridModelLoaders;

    public static Grid getGrid(String modelId) {
        if (null == gridModelLoaders) {
            synchronized (GridFactory.class) {
                if (null == gridModelLoaders) {
                    setGridModelLoaders(Arrays.asList(new XmlGridModelLoader()));
                }
            }
        }
        for (IGridModelLoader loader : gridModelLoaders) {
            Grid grid = loader.loadGridModel(modelId);
            if (null != grid) {
                return grid;
            }
        }
        return null;
    }

    public void setLoaders(List<? extends IGridModelLoader> loaders) {
        setGridModelLoaders(loaders);
    }

    public void registeGrid(String id, Grid grid) {

    }

    private static void setGridModelLoaders(List<? extends IGridModelLoader> gridModelLoaders) {
        if (null != gridModelLoaders) {
            for (IGridModelLoader loader : gridModelLoaders) {
                loader.init();
            }
            GridFactory.gridModelLoaders = gridModelLoaders;
        }
    }
}
