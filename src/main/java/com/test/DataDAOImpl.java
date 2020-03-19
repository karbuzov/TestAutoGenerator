package com.test;

import com.test.dto.ExampleParameter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataDAOImpl {


    public String getData(List<ExampleParameter> list, Long A2, String aaa) {

        String str = "";
        if (list!= null) {
            str = list.stream().map(i -> i.getStr()).collect(Collectors.joining());
        }
        return  "OUT" + str + "___" + A2 + "OUT";
    }
}
