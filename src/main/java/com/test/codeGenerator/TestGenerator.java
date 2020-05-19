package com.test.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codeGenerator.utils.ClassUtils;
import com.test.codeGenerator.dto.CallDTO;
import com.test.codeGenerator.dto.ParameterDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestGenerator {

    ObjectMapper objectMapper = new ObjectMapper();

    public TestGenerator() {
        System.out.println("****  AOP ***");
    }

    private String formatParameter(ParameterDTO param) throws Exception {
        return param.getTestParameterValue();
    }


    private void prepareParameter(ParameterDTO param) throws Exception {
        Object val = null;
        String result = "";

        if (param.getClassName() != null) {

            if (param.isClassPrimitive()) {

                switch (param.getClassName()) {
                    case "java.lang.String": {
                        val = objectMapper.readValue(param.getJsonData(), Class.forName(param.getClassName()));
                        result = "\"" + val.toString() + "\"";
                        break;
                    }

                    default: {
                        val = objectMapper.readValue(param.getJsonData(), Class.forName(param.getClassName()));
                    }
                }
            } else {
                result = "var" + (param.getIndex() + 1);

                String definition = param.getClassName() + " " + result + " = " +
                        "objectMapper.readValue(requestJson, " + getSimpleClassName(param.getClassName()) + ");";
                param.setTestParameterDefinition(definition);
            }
        } else {
            result = null;
        }

        param.setTestParameterValue(result);
    }

    public String getSimpleClassName(String fullClassName) throws Exception {
        String[] strList = fullClassName.split("\\.");
        if (strList.length == 0) {
            return "==noclass==.class";
        }
        return strList[strList.length - 1] + ".class";
    }


    public String generateTest(List<CallDTO> list) throws Exception {
        String resultStr = "";
        String call = "";
        CallDTO callData = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            callData.getParams().addAll(list.get(i).getParams());
        }


        if (callData.getParams() != null) {
            call = "(";
            boolean coma = false;
            for (ParameterDTO param : callData.getParams()) {
                prepareParameter(param);
            }
            prepareParameter(callData.getResult());
            String test = "    @Test\n" +
                    "    public void activeFreeTickets2() throws Exception {\n" +
                    "\n";
//            for (ParameterDTO param : callData.getParams()) {
            for (int i = 0; i < callData.getParams().size() - 1; i++) {
                ParameterDTO param = callData.getParams().get(i);
                if (!StringUtils.isEmpty(param.getTestParameterDefinition())) {
                    System.out.println(param.getTestParameterDefinition());

                    test += "        String requestJson = \"" + param.getJsonData().replace("\"","\\\"") + "\";\n" +
                            "        " + param.getTestParameterDefinition() + "\n" +
                            "\n";

                }
            }

            test += "        String dependencyJson = \"[{\\\"roomIDList\\\":\\\"1262\\\",\\\"bonusCode\\\":\\\"BCThu Apr 23 2020 11:13:51 GMT+0300 (RTZ 2 (зима))\\\",\\\"expirationDate\\\":1587629631901,\\\"freeTicketsType\\\":\\\"STANDARD\\\",\\\"status\\\":\\\"Pending\\\",\\\"activeFreeTickets\\\":5,\\\"playedFreeTickets\\\":0,\\\"awardingDate\\\":1586754833917,\\\"minCostTicket\\\":0.0,\\\"maxCostTicket\\\":2.0},\" +\n" +
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
                    "        BaseResponse<ActiveFreeTicketsResponse> actualResult = controller.activeFreeTickets(var1);\n" +
                    "        String actualResultJson = objectMapper.writeValueAsString(actualResult);\n" +
                    "\n" +
                    "        assertEquals(resultJson, actualResultJson);\n" +
                    "    }\n";

            for (ParameterDTO param : callData.getParams()) {

                call += coma ? ", " + formatParameter(param) : formatParameter(param);
                coma = true;
            }
            resultStr = formatParameter(callData.getResult());

        }
        return "==================== Object actualValue = " + callData.getClassName() + "." + callData.getMethodName() + call + "); \n\n\n" +
                "==================== assertEquals(" + resultStr + ", actualValue);";
    }
}
