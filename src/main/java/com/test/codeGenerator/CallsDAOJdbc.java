package com.test.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codeGenerator.dto.CallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CallsDAOJdbc extends JdbcDaoSupport {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CallsDAOJdbc(DataSource dataSource){
        setDataSource(dataSource);
    }

    public void save(CallDTO call) throws Exception{
        String uid = call.getUid();
        call.setUid("");
        String json = objectMapper.writeValueAsString(call);
        try {
            getJdbcTemplate().update("insert into z_arbuzov_tmp( requestid, text, inserted) values (?, ?, sysdate())", uid, json);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> load(String id) throws Exception {
//        create table z_arbuzov_tmp
//                (
//                        id int auto_increment,
//        requestid varchar(50) not null,
//                text MEDIUMTEXT not null,
//                inserted timestamp NOT NULL,
//                constraint table_name_pk
//        primary key (id)
//);
//
//
//        truncate table z_arbuzov_tmp;
//
//        select * from z_arbuzov_tmp
//        where text like '%getAvail%'
//        and id > -1310
//        order by  id desc;
//
//
//
//        select replace(data.json, '\\"jackpotAmountMap\\":{},', '') from (
//                select distinct concat('jsonList.add("', replace(
//                        replace(
//                                replace(
//                                        replace(text, '\\"', '<<><>234>>'),
//                                        '"', '\\"'),
//                                '<<><>234>>', '\\\\\\"'
//                        ),
//                        '\\\":\\\"\\\""', '<<><>234>>'), '");') as json
//                from z_arbuzov_tmp
//                where requestid = 'ee005758-91d1-4401-917d-d15754673850'
//        ) data
//                -- where data.json like '%INGO.PURCHASE_STOP_PER%'
//        ;


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
