package com.forms.beneform4j.excel.core.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.beneform4j.core.dao.stream.IListStreamReader;

@Component
public class DbDataService {

    private static IExampleDao dao;

    @Autowired
    public void setDao(IExampleDao dao) {
        DbDataService.dao = dao;
    }

    public static IListStreamReader<ParamEnumDef> getData(String paramCode) {
        return dao.selectListStream(paramCode);
    }
}
