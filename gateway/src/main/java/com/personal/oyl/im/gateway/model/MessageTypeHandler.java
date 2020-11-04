package com.personal.oyl.im.gateway.model;

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
public class MessageTypeHandler implements TypeHandler<MessageType> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, MessageType messageType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, messageType.getCode());
    }

    @Override
    public MessageType getResult(ResultSet resultSet, String s) throws SQLException {
        return MessageType.init(resultSet.getInt(s));
    }

    @Override
    public MessageType getResult(ResultSet resultSet, int i) throws SQLException {
        return MessageType.init(resultSet.getInt(i));
    }

    @Override
    public MessageType getResult(CallableStatement callableStatement, int i) throws SQLException {
        return MessageType.init(callableStatement.getInt(i));
    }
}
