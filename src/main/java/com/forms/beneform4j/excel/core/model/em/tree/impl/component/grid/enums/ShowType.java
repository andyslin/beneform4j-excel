package com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.enums;

import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 表格组件使用的显示类型<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public enum ShowType {
    INIT_SHOW, // 初始化显示
    INIT_HIDDEN, // 初始化隐藏
    LOCK_SHOW, // 锁定显示
    LOCK_HIDDEN, // 锁定隐藏
    NO_SHOW, // 不显示，但程序可以访问其数据
    UNKNOWN; // 未知

    public static ShowType instance(String code) {
        if (null != code) {
            code = code.trim().toUpperCase();
            for (ShowType t : values()) {
                if (t.name().equals(code)) {
                    return t;
                }
            }
        }
        return UNKNOWN;
    }

    public void setTdProperties(Td td) {
        switch (this) {
            case INIT_HIDDEN:
                td.setHidden(true);
                td.setLocked(false);
                break;
            case LOCK_SHOW:
                td.setHidden(false);
                td.setLocked(true);
                break;
            case LOCK_HIDDEN:
                td.setHidden(true);
                td.setLocked(true);
                break;
            case NO_SHOW:
                td.setHidden(true);
                td.setLocked(false);
                break;
            case INIT_SHOW:
            case UNKNOWN:
                td.setHidden(false);
                td.setLocked(false);
                break;
        }
    }
}
