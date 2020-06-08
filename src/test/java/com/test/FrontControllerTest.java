package com.test;

import com.test.codeGenerator.TestGenerator;
import com.test.codeGenerator.dto.CallDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FrontControllerTest {
    private TestGenerator testGenerator;

    @Before
    public void setUp() throws Exception {
        testGenerator = new TestGenerator();
    }

    @Test
    public void test1() throws Exception {
        List<String> jsonList = new ArrayList<>();


        jsonList.add("{\"params\":[{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.ActiveFreeTicketsRequest\",\"jsonData\":\"{\\\"playerID\\\":453816,\\\"roomID\\\":0}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"parametrized\":null,\"index\":0,\"classPrimitive\":false}],\"result\":{\"className\":\"java.util.ArrayList\",\"jsonData\":\"[{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587642892646\\\",\\\"expirationDate\\\":1594846799409,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586768121885,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0}]\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"parametrized\":[\"com.pragmaticplay.bingo.bonusservice.controllers.dto.FreeRoundBonus\"],\"index\":1,\"classPrimitive\":false},\"className\":\"com.pragmaticplay.bingo.bonusservice.services.IntegrationFreeTicketsManagerImpl\",\"methodName\":\"activeFreeTickets\",\"uid\":\"2b1f581a-dd5d-4025-b983-e3faae12ff29\"}");
        jsonList.add("{\"params\":[{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.ActiveFreeTicketsRequest\",\"jsonData\":\"{\\\"playerID\\\":453816,\\\"roomID\\\":0}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"parametrized\":null,\"index\":0,\"classPrimitive\":false}],\"result\":{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.BaseResponse\",\"jsonData\":\"{\\\"status\\\":0,\\\"description\\\":\\\"OK.\\\",\\\"value\\\":{\\\"bonuses\\\":null}}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"parametrized\":null,\"index\":1,\"classPrimitive\":false},\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.IntegrationFreeTicketsController\",\"methodName\":\"activeFreeTickets\",\"uid\":\"2b1f581a-dd5d-4025-b983-e3faae12ff29\"}");


        List<CallDTO> list = testGenerator.getCallsFromJsonList(jsonList);
//        if (list == null)
//            return null;

        String result = testGenerator.generateTest(list);
        String expected = "    @Test\n" +
                "    public void activeFreeTickets() throws Exception {\n" +
                "\n" +
                "        String mock1Json = \"[{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587642892646\\\",\\\"expirationDate\\\":1594846799409,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586768121885,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0}]\";\n" +
                "        ArrayList<FreeRoundBonus> mock1 = objectMapper.readValue(mock1Json, ArrayList.class);\n" +
                "\n" +
                "\n" +
                "        when(manager.activeFreeTickets(any())).thenReturn(mock1);\n" +
                "\n" +
                "        String requestJson = \"{\\\"playerID\\\":453816,\\\"roomID\\\":0}\";\n" +
                "        ActiveFreeTicketsRequest request = objectMapper.readValue(requestJson, ActiveFreeTicketsRequest.class);\n" +
                "\n" +
                "        String responseJson = \"[{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587642892646\\\",\\\"expirationDate\\\":1594846799409,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586768121885,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0}]\";\n" +
                "        ArrayList<FreeRoundBonus> actualResponse = objectMapper.readValue(responseJson, ArrayList.class);\n" +
                "\n" +
                "        String resultJson = \"{\\\"status\\\":0,\\\"description\\\":\\\"OK.\\\",\\\"value\\\":{\\\"bonuses\\\":null}}\";\n" +
                "        BaseResponse result = objectMapper.readValue(resultJson, BaseResponse.class);\n" +
                "\n" +
                "        BaseResponse<> actualResult = controller.activeFreeTickets(request);\n" +
                "        String actualResultJson = objectMapper.writeValueAsString(actualResult);\n" +
                "\n" +
                "        assertEquals(resultJson, actualResultJson);\n" +
                "    }\n";

        assertEquals(expected, result);
    }

    @Test
    public void test2() throws Exception {
        List<String> jsonList = new ArrayList<>();

        jsonList.add("{\"params\":[{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.CreateFreeTicketsRequest\",\"classPrimitive\":false,\"jsonData\":\"{\\\"playerID\\\":453816,\\\"brandID\\\":1051,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"roomIDList\\\":[1262],\\\"freeTickets\\\":5,\\\"minCostTicket\\\":null,\\\"maxCostTicket\\\":null,\\\"bonusCode\\\":\\\"BC1592333087794\\\",\\\"expirationDate\\\":0}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"parametrized\":null,\"index\":0}],\"result\":{\"className\":\"java.lang.Long\",\"classPrimitive\":true,\"jsonData\":\"0\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"parametrized\":null,\"index\":1},\"className\":\"com.pragmaticplay.bingo.bonusservice.services.IntegrationFreeTicketsManagerImpl\",\"methodName\":\"create\",\"uid\":\"aaa47db9-8d81-44ed-a849-7c9e0b1c062d\"}");
        jsonList.add("{\"params\":[{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.CreateFreeTicketsRequest\",\"classPrimitive\":false,\"jsonData\":\"{\\\"playerID\\\":453816,\\\"brandID\\\":1051,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"roomIDList\\\":[1262],\\\"freeTickets\\\":5,\\\"minCostTicket\\\":null,\\\"maxCostTicket\\\":null,\\\"bonusCode\\\":\\\"BC1592333087794\\\",\\\"expirationDate\\\":0}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"parametrized\":null,\"index\":0}],\"result\":{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.BaseResponse\",\"classPrimitive\":false,\"jsonData\":\"{\\\"status\\\":0,\\\"description\\\":\\\"OK.\\\",\\\"value\\\":{\\\"providerPlayerId\\\":453816,\\\"bonusCode\\\":\\\"BC1592333087794\\\"}}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"parametrized\":null,\"index\":1},\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.IntegrationFreeTicketsController\",\"methodName\":\"createFreeTickets\",\"uid\":\"aaa47db9-8d81-44ed-a849-7c9e0b1c062d\"}");


        List<CallDTO> list = testGenerator.getCallsFromJsonList(jsonList);
//        if (list == null)
//            return null;

        String result = testGenerator.generateTest(list);
        String expected = "    @Test\n" +
                "    public void createFreeTickets() throws Exception {\n" +
                "\n" +
                "        Long mock1 = 0L;\n" +
                "\n" +
                "\n" +
                "        when(manager.create(any())).thenReturn(mock1);\n" +
                "\n" +
                "        String requestJson = \"{\\\"playerID\\\":453816,\\\"brandID\\\":1051,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"roomIDList\\\":[1262],\\\"freeTickets\\\":5,\\\"minCostTicket\\\":null,\\\"maxCostTicket\\\":null,\\\"bonusCode\\\":\\\"BC1592333087794\\\",\\\"expirationDate\\\":0}\";\n" +
                "        CreateFreeTicketsRequest request = objectMapper.readValue(requestJson, CreateFreeTicketsRequest.class);\n" +
                "\n" +
                "        String resultJson = \"{\\\"status\\\":0,\\\"description\\\":\\\"OK.\\\",\\\"value\\\":{\\\"providerPlayerId\\\":453816,\\\"bonusCode\\\":\\\"BC1592333087794\\\"}}\";\n" +
                "        BaseResponse result = objectMapper.readValue(resultJson, BaseResponse.class);\n" +
                "\n" +
                "        BaseResponse<> actualResult = controller.createFreeTickets(request);\n" +
                "        String actualResultJson = objectMapper.writeValueAsString(actualResult);\n" +
                "\n" +
                "        assertEquals(resultJson, actualResultJson);\n" +
                "    }\n";

        assertEquals(expected, result);
    }

}