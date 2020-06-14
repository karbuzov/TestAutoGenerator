package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codeGenerator.TestGenerator;
import com.test.codeGenerator.CallsDAOJdbc;
import com.test.codeGenerator.dto.CallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FrontController {
    private List<String> arr = new ArrayList<>();
    private int pageSize;

    DataDAOImpl dataDAO;
    CallsDAOJdbc callsDAOJdbc;
    TestGenerator testGenerator;

    @Autowired
    public FrontController(DataDAOImpl dataDAO, CallsDAOJdbc callsDAOJdbc, TestGenerator testGenerator) {
        this.dataDAO = dataDAO;
        this.callsDAOJdbc = callsDAOJdbc;
        this.pageSize = 3;
        this.testGenerator = testGenerator;
    }


    public String getPage(int pageNumber) {
        try {
            List<String> jsonList = callsDAOJdbc.load("6a6d5ce6-f52d-4a8c-93f2-6da417cbfe51");

            List<CallDTO> list = testGenerator.getCallsFromJsonList(jsonList);
            if (list == null) return null;

            String result = testGenerator.generateTest(list);
            System.out.println(result);

//{"params":[{"className":"com.pragmaticplay.bingo.bonusservice.controllers.dto.ActiveFreeTicketsRequest","jsonData":"{\"playerID\":453816,\"roomID\":0}","testParameterValue":null,"testParameterDefinition":null,"index":0,"classPrimitive":false}],"result":{"className":"java.util.ArrayList","jsonData":"" +
//"[{\"roomIDList\":\"1262\",\"bonusCode\":\"BC1587642892646\",\"expirationDate\":1594846799409,\"freeTicketsType\":\"STANDARD\",\"status\":\"Pending\",\"activeFreeTickets\":5,\"playedFreeTickets\":0,\"awardingDate\":1586768121885,\"minCostTicket\":0.0,\"maxCostTicket\":0.0}]"
//,"testParameterValue":null,"testParameterDefinition":null,"index":1,"classPrimitive":false},"className":"com.pragmaticplay.bingo.bonusservice.services.IntegrationFreeTicketsManagerImpl","methodName":"activeFreeTickets","uid":"5ed06a88-2044-4690-8eba-f2416cbe9815"}
//{"params":[{"className":"com.pragmaticplay.bingo.bonusservice.controllers.dto.ActiveFreeTicketsRequest","jsonData":"{\"playerID\":453816,\"roomID\":0}","testParameterValue":null,"testParameterDefinition":null,"index":0,"classPrimitive":false}],"result":{"className":"com.pragmaticplay.bingo.bonusservice.controllers.dto.BaseResponse","jsonData":"{\"status\":0,\"description\":\"OK.\",\"value\":{\"bonuses\":null}}","testParameterValue":null,"testParameterDefinition":null,"index":1,"classPrimitive":false},"className":"com.pragmaticplay.bingo.bonusservice.controllers.IntegrationFreeTicketsController","methodName":"activeFreeTickets","uid":"5ed06a88-2044-4690-8eba-f2416cbe9815"}



            String test = "    @Test\n" +
                    "    public void activeFreeTickets2() throws Exception {\n" +
                    "\n" +
                    "        String requestJson = \"{\\\"playerID\\\":453816,\\\"roomID\\\":1242}\";\n" +
                    "        ActiveFreeTicketsRequest var1 = objectMapper.readValue(requestJson, ActiveFreeTicketsRequest.class);\n" +
                    "\n" +
                    "        String dependencyJson = \"[{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BCThu Apr 23 2020 11:13:51 GMT+0300 (RTZ 2 (зима))\\\",\\\"expirationDate\\\":1587629631901,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586754833917,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC23\\\",\\\"expirationDate\\\":1587629651179,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586754853150,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587629820144\\\",\\\"expirationDate\\\":1587629820144,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586755022146,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587642892646\\\",\\\"expirationDate\\\":1594846799409,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586768121885,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587639868368\\\",\\\"expirationDate\\\":1587639868368,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586765070786,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587641264899\\\",\\\"expirationDate\\\":1587641264899,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586766481494,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587641630982\\\",\\\"expirationDate\\\":1587641630982,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586766836129,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587641658558\\\",\\\"expirationDate\\\":1587641630982,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586766863694,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587640566109\\\",\\\"expirationDate\\\":1587640566109,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586765783148,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587640595613\\\",\\\"expirationDate\\\":1587640595613,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586765800522,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":1000.0}]\";\n" +
                    "        List<FreeRoundBonus> var2 = objectMapper.readValue(dependencyJson, ArrayList.class);\n" +
                    "        String resultJson = \"{\\\"status\\\":0,\\\"description\\\":\\\"OK.\\\",\\\"value\\\":{\\\"bonuses\\\":[{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BCThu Apr 23 2020 11:13:51 GMT+0300 (RTZ 2 (зима))\\\",\\\"expirationDate\\\":1587629631901,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586754833917,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC23\\\",\\\"expirationDate\\\":1587629651179,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586754853150,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587629820144\\\",\\\"expirationDate\\\":1587629820144,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586755022146,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587642892646\\\",\\\"expirationDate\\\":1594846799409,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586768121885,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587639868368\\\",\\\"expirationDate\\\":1587639868368,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586765070786,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587641264899\\\",\\\"expirationDate\\\":1587641264899,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586766481494,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587641630982\\\",\\\"expirationDate\\\":1587641630982,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586766836129,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587641658558\\\",\\\"expirationDate\\\":1587641630982,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586766863694,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587640566109\\\",\\\"expirationDate\\\":1587640566109,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586765783148,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
                    "                \"{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587640595613\\\",\\\"expirationDate\\\":1587640595613,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586765800522,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":1000.0}]}}\";\n" +
                    "        BaseResponse<GetPlayersBonusResponse> expected = objectMapper.readValue(resultJson, BaseResponse.class);\n" +
                    "        when(freeTicketsManager.activeFreeTickets(any())).thenReturn(var2);\n" +
                    "\n" +
                    "\n" +
                    "        BaseResponse<> actualResult = controller.activeFreeTickets(var1);\n" +
                    "        String actualResultJson = objectMapper.writeValueAsString(actualResult);\n" +
                    "\n" +
                    "        assertEquals(resultJson, actualResultJson);\n" +
                    "    }\n";


        }catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
    }


    @RequestMapping(value = "/callDTO/", method = RequestMethod.POST)
    public void doit(@RequestBody CallDTO data) throws Exception {

        callsDAOJdbc.save(data);

//        getPage(1);
    }

}
