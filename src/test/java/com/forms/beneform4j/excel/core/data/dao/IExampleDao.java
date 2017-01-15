package com.forms.beneform4j.excel.core.data.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.beneform4j.core.dao.annotation.FetchSize;
import com.forms.beneform4j.core.dao.stream.IListStreamReader;

@Repository
public interface IExampleDao {

    @FetchSize(10000)
    public IListStreamReader<ParamEnumDef> selectListStream(@Param("paramGroup") String paramGroup);

    public IListStreamReader<ParamEnumDef> selectListStream(@Param("paramGroup") String paramGroup, @FetchSize int fetchSize);
}
