package com.test;

import com.test.dto.ExampleParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//        return result.stream().map(i -> i).collect();
        List<ExampleParameter> lst = new ArrayList<>();
        lst.add(new ExampleParameter("param" + pageNumber, pageNumber));
        result.add(dataDAO.getData(null, 123L, "3d_param"));
        return result.toString() + "=======================";
    }

    @RequestMapping("/do/{param}")
    public void doit(@PathVariable("param") final int param){
        getPage(param);
    }

}
