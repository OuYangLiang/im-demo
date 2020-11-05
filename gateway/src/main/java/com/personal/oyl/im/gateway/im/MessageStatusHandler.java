package com.personal.oyl.im.gateway.im;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author OuYang Liang
 * @since 2020-11-04
 */
public class MessageStatusHandler implements TypeHandler<MessageStatus> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, MessageStatus messageStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, messageStatus.getCode());
    }

    @Override
    public MessageStatus getResult(ResultSet resultSet, String s) throws SQLException {
        return MessageStatus.init(resultSet.getInt(s));
    }

    @Override
    public MessageStatus getResult(ResultSet resultSet, int i) throws SQLException {
        return MessageStatus.init(resultSet.getInt(i));
    }

    @Override
    public MessageStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        return MessageStatus.init(callableStatement.getInt(i));
    }
}
