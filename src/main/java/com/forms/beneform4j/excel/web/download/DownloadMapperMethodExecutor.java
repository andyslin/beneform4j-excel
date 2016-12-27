package com.forms.beneform4j.excel.web.download;

import java.util.List;

import org.apache.ibatis.mapping.SqlCommandType;

import com.forms.beneform4j.core.dao.mybatis.mapper.IMapperMethodExecutor;
import com.forms.beneform4j.core.dao.mybatis.mapper.MapperMethod;
import com.forms.beneform4j.core.dao.mybatis.mapper.MapperMethod.SqlCommand;
import com.forms.beneform4j.core.dao.stream.IListStreamReader;
import com.forms.beneform4j.excel.export.datastream.handler.IDataStreamHandler;
import com.forms.beneform4j.excel.export.datastream.wrap.IDataStreamHandlerWrap;

public class DownloadMapperMethodExecutor implements IMapperMethodExecutor {

    private final ThreadLocal<IDataStreamHandlerWrap> wraps = new ThreadLocal<IDataStreamHandlerWrap>();

    @Override
    public boolean isSupport(MapperMethod mapperMethod) {
        if (SqlCommandType.SELECT.equals(mapperMethod.getCommand().getType()) && mapperMethod.getMethodSignature().returnsMany()) {
            return true;
        }
        return false;
    }

    @Override
    public Object execute(MapperMethod mapperMethod, Object[] args) {
        SqlCommand command = mapperMethod.getCommand();
        String sqlId = command.getName();
        Object parameter = mapperMethod.getMethodSignature().convertArgsToSqlCommandParam(args);
        int fetchSize = 1000;

        final IListStreamReader<?> reader = command.getDao().selectListStream(sqlId, parameter, fetchSize);
        wraps.set(new IDataStreamHandlerWrap() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public Object handler(IDataStreamHandler handler) {
                List list = null;
                int index = 0;
                int batch = 1;
                while ((list = reader.read()) != null) {
                    handler.handler(list, index, batch);
                    index += list.size();
                    batch++;
                }
                return handler.getResult();
            }
        });
        return null;
    }

}
