package com.test.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codeGenerator.dto.CallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CallsDAOJdbc extends JdbcDaoSupport {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CallsDAOJdbc(DataSource dataSource){
        setDataSource(dataSource);
    }

    public void save(CallDTO call) throws Exception{
        String json = objectMapper.writeValueAsString(call);
        getJdbcTemplate().update("insert into z_arbuzov_tmp(id, text) values (?, ?)", call.getUid(), json);
    }
}
