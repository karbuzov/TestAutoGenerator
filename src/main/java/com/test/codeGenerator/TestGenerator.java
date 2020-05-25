package com.test.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.codeGenerator.dto.CallDTO;
import com.test.codeGenerator.dto.ParameterDTO;
import org.springframework.stereotype.Service;

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
                result = "varr123asdf";

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

                test += formatReqResp(list, i, "mock" + (i + 1), false);
//                test += "\n" +
//                        "        when(freeTicketsManager." + callData.getMethodName() + "(any())).thenReturn(" + "mock" + (i + 1) + ");\n\n";

                for (ParameterDTO param : callData.getParams()) {

                    call += coma ? ", " + formatParameter(param) : formatParameter(param);
                    coma = true;
                }
                resultStr = formatParameter(callData.getResult());

            }
        }

//        test += formatReqResp(list,list.size() - 1, "request", true);
//

        return test;
    }

    private String formatReqResp(List<CallDTO> list, int index, String varName, boolean isResult) {
        CallDTO callData = list.get(index);

        String dd = "        String " + varName + "Json = \"" + callData.getResult().getJsonData().replace("\"", "\\\"") + "\";\n";
        dd += "        " + callData.getResult().getTestParameterDefinition();

        dd = dd.replace(".class varr123asdf ", " " + varName + " ");
        dd = dd.replace(".readValue(requestJson, ", ".readValue(" + varName + "Json, ");

int i = 0;
        String test = dd +
                "\n" +
                "\n" +
                "\n" +
                "        when(manager." + callData.getMethodName() + "(any())).thenReturn(" + "mock" + (i + 1) + ");\n" +
                "\n" + "";

        dd = "        String requestJson = \"" + list.get(i).getParams().get(0).getJsonData()
                .replace("\"", "\\\"") + "\";\n";
        dd += "        " + list.get(i + 1).getParams().get(0).getTestParameterDefinition();

        dd = dd.replace(".class varr123asdf ", " request ");
        dd = dd.replace(".readValue(requestJson, ", ".readValue(requestJson, ");
        dd = dd + "  ";


        test = test + dd +
                "\n" +

                "\n" + "";

        dd = "        String responseJson = \"" + list.get(i).getResult().getJsonData().replace("\"", "\\\"") + "\";\n";
        dd += "        " + list.get(i).getResult().getTestParameterDefinition();

        dd = dd.replace(".class varr123asdf ", " actualResponse ");
        dd = dd.replace(".readValue(requestJson, ", ".readValue(responseJson, ");
        dd = dd + "\n";

        test = test + dd +

                "\n";

        dd = "        String resultJson = \"" + list.get(i + 1).getResult().getJsonData()
                .replace("\"", "\\\"") + "\";\n";
        dd += "        " + list.get(i + 1).getResult().getTestParameterDefinition();

        dd = dd.replace(".class varr123asdf ", " result ");
        dd = dd.replace(".readValue(requestJson, ", ".readValue(resultJson, ");
        dd = dd + "\n";

        test = test + dd +
                "\n" +
                "        BaseResponse<ActiveFreeTicketsResponse> actualResult = controller.activeFreeTickets(request);\n" +
                "        String actualResultJson = objectMapper.writeValueAsString(result);\n" +
                "\n" +
                "        assertEquals(resultJson, actualResultJson);\n" +
                "    }\n";


        return test;
    }
}
