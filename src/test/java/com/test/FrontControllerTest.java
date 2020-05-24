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
    public void getPage() throws Exception {
        List<String> jsonList = new ArrayList<>();
        jsonList.add("{\"params\":[{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.ActiveFreeTicketsRequest\",\"jsonData\":\"{\\\"playerID\\\":453816,\\\"roomID\\\":0}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"index\":0,\"classPrimitive\":false}],\"result\":{\"className\":\"java.util.ArrayList\",\"jsonData\":\"[{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587642892646\\\",\\\"expirationDate\\\":1594846799409,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586768121885,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0}]\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"index\":1,\"classPrimitive\":false},\"className\":\"com.pragmaticplay.bingo.bonusservice.services.IntegrationFreeTicketsManagerImpl\",\"methodName\":\"activeFreeTickets\",\"uid\":\"5ed06a88-2044-4690-8eba-f2416cbe9815\"}");
        jsonList.add("{\"params\":[{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.ActiveFreeTicketsRequest\",\"jsonData\":\"{\\\"playerID\\\":453816,\\\"roomID\\\":0}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"index\":0,\"classPrimitive\":false}],\"result\":{\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.dto.BaseResponse\",\"jsonData\":\"{\\\"status\\\":0,\\\"description\\\":\\\"OK.\\\",\\\"value\\\":{\\\"bonuses\\\":null}}\",\"testParameterValue\":null,\"testParameterDefinition\":null,\"index\":1,\"classPrimitive\":false},\"className\":\"com.pragmaticplay.bingo.bonusservice.controllers.IntegrationFreeTicketsController\",\"methodName\":\"activeFreeTickets\",\"uid\":\"5ed06a88-2044-4690-8eba-f2416cbe9815\"}");

        List<CallDTO> list = testGenerator.getCallsFromJsonList(jsonList);
//        if (list == null)
//            return null;

        String result = testGenerator.generateTest(list);
        String expected = "    @Test\n" +
                "    public void activeFreeTickets2() throws Exception {\n" +
                "\n" +
                "        String mock1Json = \"[{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BC1587642892646\\\",\\\"expirationDate\\\":1594846799409,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586768121885,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":0.0}]\";\n" +
                "        ArrayList<FreeRoundBonus> mock1 = objectMapper.readValue(mock1Json, ArrayList.class);\n" +
                "\n" +
                "\n" +
                "        when(freeTicketsManager.activeFreeTickets(any())).thenReturn(mock1);\n" +
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
                "        BaseResponse<ActiveFreeTicketsResponse> actualResult = controller.activeFreeTickets(request);\n" +
                "        String actualResultJson = objectMapper.writeValueAsString(result);\n" +
                "\n" +
                "        assertEquals(resultJson, actualResultJson);\n" +
                "    }\n";

        assertEquals(expected, result);
    }

}