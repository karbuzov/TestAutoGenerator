package com.test.codeGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.util.StringUtils;
import com.test.codeGenerator.dto.CallDTO;
import com.test.codeGenerator.dto.ParameterDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

                String definition = getSimpleClassName(param.getClassName(), false);
                if (param.getParametrized() != null && !param.getParametrized().isEmpty()) {
                    definition += "<" +
                            StringUtils.join(param.getParametrized().stream()
                                    .map(i -> getSimpleClassName(i, false))
                                    .collect(Collectors.toList()), ",") +
                            ">";
                }

                definition += " " + result + " = " +
                        "objectMapper.readValue(requestJson, " + getSimpleClassName(param.getClassName(), true) + ");";
                param.setTestParameterDefinition(definition);
            }
        } else {
            result = null;
        }

        param.setTestParameterValue(result);
    }

    public String getSimpleClassName(String fullClassName, boolean addClass) {
        String[] strList = fullClassName.split("\\.");
        if (strList.length == 0) {
            return "==noclass==.class";
        }
        return strList[strList.length - 1] + (addClass ? ".class":"");
    }


    public String generateTest(List<CallDTO> list) throws Exception {
        String resultStr = "";
        String call = "";

        CallDTO callData = null;
        for (int i = 0; i < list.size(); i++) {
            callData = list.get(i);
            for (ParameterDTO param : callData.getParams()) {
                prepareParameter(param);
            }
            prepareParameter(callData.getResult());
        }
        String test = "    @Test\n" +
                "    public void " + callData.getMethodName()+ "() throws Exception {\n" +
                "\n";
        for (int i = 0; i < list.size()-1; i++) {
            callData = list.get(i);
            if (callData.getParams() != null) {

                boolean coma = false;

//            for (ParameterDTO param : callData.getParams()) {

                test += formatReqResp(list, i, "mock" + (i + 1), false);
                test += formatResponse(list, i, "mock" + (i + 1), false);
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


        String mocksDefinition = "";
        int i = index;
            String def = "";
            String dd = "        String " + varName + "Json = \"" + list.get(i).getResult().getJsonData().replace("\"", "\\\"") + "\";\n";
            ParameterDTO result = list.get(i).getResult();

            if (result.isClassPrimitive()) {
                dd = "";
                String type = getSimpleClassName(result.getClassName(), false);
                def = type + " " + varName + " = " + "" + result.getJsonData();

                if (type.equals("Long")) {
                    def = def + "L";
                }
                def = def + ";";
            } else {
                def += result.getTestParameterDefinition();
            }
            dd += "        " + def;

            dd = dd.replace(" varr123asdf ", " " + varName + " ");
            dd = dd.replace(".readValue(requestJson, ", ".readValue(" + varName + "Json, ");

            String paramDefinitionlist = "";

            int j = 0;
            String paramList = "";
            for (ParameterDTO param : list.get(i + 0).getParams()) {
                String paramName = "";
                if (param.isClassPrimitive()) {
                    if (param.getClassName() != null) {
                        paramName = "param" + (i + 1) + "_" + (j + 1);

                        String paramType = getSimpleClassName(param.getClassName(), false);
                        paramDefinitionlist += getStringSwitch(paramName, paramType, param);
                    } else {
                        paramName = "null";
                    }

                    if (paramList.length() > 0) {
                        paramList += ", ";
                    }
                    paramList += paramName;
                } else {
                    if (paramList.length() > 0) {
                        paramList += ", ";
                    }
                    paramList += "any()";
                }
                j++;
            }
            String test = dd +
                    "\n" +
                    "\n" + paramDefinitionlist + "\n" +
                    "        when(manager." + list.get(i).getMethodName() + "(" + paramList + ")).thenReturn(" + "mock" + (i + 1) + ");\n" +
                    "\n" + "";
            mocksDefinition += test;


        String g = "";
        dd = "";
        i = list.size() - 1;
        for(ParameterDTO param: list.get(i).getParams()) {
            if (param.isClassPrimitive()) {
                g = param.getTestParameterDefinition();
            } else {
                dd = "        String requestJson = \"" + list.get(i).getParams().get(0).getJsonData()
                        .replace("\"", "\\\"") + "\";\n";
                g = param.getTestParameterDefinition();
            }

        }
        dd += "        " + g;

        dd = dd.replace(" varr123asdf ", " request ");
        dd = dd.replace(".readValue(requestJson, ", ".readValue(requestJson, ");
//        dd = dd + "  ";


        mocksDefinition = mocksDefinition + dd +
                "\n" +

                "\n" + "";
        return mocksDefinition;
    }

    private String formatResponse(List<CallDTO> list, int index, String varName, boolean isResult) {
        String test = "";
        String dd = "";
        int i = index;
        if (list.get(i).getResult().getTestParameterDefinition() != null) {
            dd = "        String responseJson = \"" + list.get(i).getResult().getJsonData().replace("\"", "\\\"") + "\";\n";
            dd += "        " + list.get(i).getResult().getTestParameterDefinition();
            dd = dd.replace(" varr123asdf ", " actualResponse ");
            dd = dd.replace(".readValue(requestJson, ", ".readValue(responseJson, ");
            dd = dd + "\n";

            test = test + dd + "\n";
        }

        dd = "        String resultJson = \"" + list.get(i + 1).getResult().getJsonData()
                .replace("\"", "\\\"") + "\";\n";
        dd += "        " + list.get(i + 1).getResult().getTestParameterDefinition();

        dd = dd.replace(" varr123asdf ", " result ");
        dd = dd.replace(".readValue(requestJson, ", ".readValue(resultJson, ");
        dd = dd + "\n";

        test = test + dd +
                "\n" +
                "        BaseResponse<> actualResult = controller." + list.get(list.size() - 1).getMethodName() + "(request);\n" +
                "        String actualResultJson = objectMapper.writeValueAsString(actualResult);\n" +
                "\n" +
                "        assertEquals(resultJson, actualResultJson);\n" +
                "    }\n";


        return test;
    }

    private String getStringSwitch(String paramName, String paramType, ParameterDTO param) {
        String paramDefinitionlist = "";
        switch (paramType){
            case "String": {
                paramDefinitionlist += "        " + paramType + " " + paramName + " = " + param.getJsonData();
                break;
            }

            default:{
                paramDefinitionlist += "        " + paramType + " " + paramName + " = " + param.getJsonData();
            }
        }

        return paramDefinitionlist + ";\n";
    }
}
