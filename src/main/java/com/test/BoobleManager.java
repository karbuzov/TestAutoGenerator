package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoobleManager {
    private List<String> arr = new ArrayList<>();
    private int pageSize;


    DataDAOImpl dataDAO;

    @Autowired
    public BoobleManager(DataDAOImpl dataDAO) {
        this.dataDAO = dataDAO;
    }

    public BoobleManager(List<String> arr, int pageSize) {
        this.arr = arr;
        this.pageSize = 3;
    }
    //@Value("${adfasdf}")

    public List<String> getPage(int pageNumber) {
        List<String> result = arr.subList((pageNumber-1) * pageSize, pageNumber * pageSize);
//        for (String str : result){
//
//        }
        result.add(dataDAO.getData(1, 123L));

//        return result.stream().map(i -> i).collect();
        return result;
    }

}
