package com.seojs.ptmanager.interceptor;

import com.seojs.ptmanager.domain.message.Message;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.time.LocalDateTime;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AuditingInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT) {
            Object parameter = invocation.getArgs()[1];
            // Message 객체인지 확인하고 CreatedDate 필드 설정
            if (parameter instanceof Message) {
                Message message = (Message) parameter;
                message.setCreatedDate(LocalDateTime.now());
            }
        }

        return invocation.proceed();
    }
}
