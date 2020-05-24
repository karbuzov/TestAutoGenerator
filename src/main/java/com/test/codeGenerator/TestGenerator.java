package com.test.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codeGenerator.dto.CallDTO;
import com.test.codeGenerator.dto.ParameterDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
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

    public List<CallDTO> getCallsFromJsonList(List<String> jsonList) {
        List<CallDTO> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();


        for (String json : jsonList) {
            try {
                list.add(objectMapper.readValue(json, CallDTO.class));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return list;
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

                String definition = getSimpleClassName(param.getClassName()) + " " + result + " = " +
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


        for (int i = 0; i < list.size(); i++) {
            CallDTO callData = list.get(i);
            for (ParameterDTO param : callData.getParams()) {
                prepareParameter(param);
            }
            prepareParameter(callData.getResult());
        }
        String test = "    @Test\n" +
                "    public void activeFreeTickets2() throws Exception {\n" +
                "\n";
        for (int i = 0; i < list.size()-1; i++) {
            CallDTO callData = list.get(i);
            if (callData.getParams() != null) {

                boolean coma = false;

//            for (ParameterDTO param : callData.getParams()) {

                test += formatReqResp(callData, "mock" + (i + 1));
                test += "\n" +
                        "        when(freeTicketsManager." + callData.getMethodName() + "(any())).thenReturn(" + "mock" + (i + 1) + ");\n\n";

                for (ParameterDTO param : callData.getParams()) {

                    call += coma ? ", " + formatParameter(param) : formatParameter(param);
                    coma = true;
                }
                resultStr = formatParameter(callData.getResult());

            }
        }

        CallDTO resultData = list.get(list.size() - 1);
        test += formatReqResp(resultData, "resp");

        test += "\n" +
                "\n" +
                "\n" +
                "        BaseResponse<ActiveFreeTicketsResponse> actualResult = controller.activeFreeTickets(var1);\n" +
                "        String actualResultJson = objectMapper.writeValueAsString(actualResult);\n" +
                "\n" +
                "        assertEquals(resultJson, actualResultJson);\n" +
                "    }\n";

        return test;
    }

    private String formatReqResp(CallDTO callData, String varName) {
        String test = "";
        for (int i = 0; i < callData.getParams().size() - 1; i++) {
            ParameterDTO param = callData.getParams().get(i);
            if (!StringUtils.isEmpty(param.getTestParameterDefinition())) {
                System.out.println(param.getTestParameterDefinition());

                test += "        String " + varName + "Json = \"" + param.getJsonData().replace("\"","\\\"") + "\";\n" +
                        "        " + param.getTestParameterDefinition().replace("(requestJson", "(" + varName + "Json") + "\n" +
                        "\n";

            }
        }

        test += "        String resultJson = \"" + callData.getResult().getJsonData().replace("\"","\\\"") + "\";\n" +
                "        " + callData.getResult().getTestParameterDefinition().replace("(requestJson", "(resultJson")
                .replace("var2 =", "result =")

                + "\n" +
                "\n// ========================================\n";

        return test;
    }
}
