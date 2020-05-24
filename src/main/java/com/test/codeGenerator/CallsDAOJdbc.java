package com.test.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codeGenerator.dto.CallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@Repository
public class CallsDAOJdbc extends JdbcDaoSupport {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CallsDAOJdbc(DataSource dataSource){
        setDataSource(dataSource);
    }

    public void save(CallDTO call) throws Exception{
        String json = objectMapper.writeValueAsString(call);
        getJdbcTemplate().update("insert into z_arbuzov_tmp(id, requestid, text, inserted) values (sq_z_arbuzov_tmp.nextval, ?, ?, sysdate)", call.getUid(), json);
    }


    public List<String> load(String id) throws Exception {
        String sql = "select id, requestid, text, inserted " +
                "from z_arbuzov_tmp " +
                "where requestid = ? " +
                "order by id";
        return getJdbcTemplate().query(sql, new Object[]{id}, (rs, i) ->
        {
            String json = rs.getString("text");
            return json;
        });
    }

}
