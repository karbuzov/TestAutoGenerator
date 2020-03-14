package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
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
        this.pageSize = 3;
    }


    public List<String> getPage(int pageNumber) {
        List<String> result = new ArrayList<>();
//        arr.subList((pageNumber-1) * pageSize, pageNumber * pageSize)
        result.add(dataDAO.getData(pageNumber, 123L));
        System.out.println( "============ok");
//        return result.stream().map(i -> i).collect();
        return result;
    }

    @PostConstruct
    public void doit(){
        getPage(1);
    }

}
