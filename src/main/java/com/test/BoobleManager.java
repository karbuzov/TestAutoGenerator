package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BoobleManager {
    private List<String> arr = new ArrayList<>();
    private int pageSize;

    DataDAOImpl dataDAO;

    @Autowired
    public BoobleManager(DataDAOImpl dataDAO) {
        this.dataDAO = dataDAO;
        this.pageSize = 3;
    }


    public String getPage(int pageNumber) {
        List<String> result = new ArrayList<>();
//        arr.subList((pageNumber-1) * pageSize, pageNumber * pageSize)
        System.out.println( "============ok");
        System.out.println( "============ok");
        System.out.println( "============ok");
        System.out.println( "============ok");
        System.out.println( "============ok");
//        return result.stream().map(i -> i).collect();
        result.add(dataDAO.getData(pageNumber, 123L));
        return result.toString() + "=======================";
    }

    @RequestMapping("/do")
    public void doit(){
        getPage(1);
    }

}
