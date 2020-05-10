package com.test;

import com.test.codeGenerator.CallsDAOJdbc;
import com.test.codeGenerator.dto.CallDTO;
import com.test.codeGenerator.dto.ExampleParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FrontController {
    private List<String> arr = new ArrayList<>();
    private int pageSize;

    DataDAOImpl dataDAO;
    CallsDAOJdbc callsDAOJdbc;

    @Autowired
    public FrontController(DataDAOImpl dataDAO, CallsDAOJdbc callsDAOJdbc) {
        this.dataDAO = dataDAO;
        this.callsDAOJdbc = callsDAOJdbc;
        this.pageSize = 3;
    }


    public String getPage(int pageNumber) {
        List<String> result = new ArrayList<>();
//        arr.subList((pageNumber-1) * pageSize, pageNumber * pageSize)
        System.out.println( "============ok");
//        return result.stream().map(i -> i).collect();
        List<ExampleParameter> lst = new ArrayList<>();
        lst.add(new ExampleParameter("param" + pageNumber, pageNumber));
        result.add(dataDAO.getData(lst, null, "3d_param"));
        return result.toString() + "=======================";
    }

    @RequestMapping(value = "/callDTO/", method = RequestMethod.POST)
    public void doit(@RequestBody CallDTO data) throws Exception {

        callsDAOJdbc.save(data);

        getPage(1);
    }

}
