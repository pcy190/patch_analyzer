import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HAPPY
 */
class Utils {


    private static String readFromFile(String pathname) throws IOException {
        StringBuilder result = new StringBuilder();
        File filename = new File(pathname);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line;
        line = br.readLine();
        while (line != null) {
            result.append(line);
            line = br.readLine();
        }
        return result.toString();
    }


    private static String parseJavaFromSmali(String prefixDir, String filename) {
        if (!prefixDir.endsWith(File.separator)) {
            prefixDir = prefixDir + File.separator;
        }
        filename = filename.replaceAll("\\.smali", ".java");
        filename = filename.replaceAll("//", "\\");
        filename = filename.replaceFirst("\\$[^.]+", "");
        filename = filename.replace(".1.", ".");
        return prefixDir + filename;
    }

    static Map<String[], Map<String, String>> readJsonData(String sourcePkgName, String targetPkgName, String basePath, String jsonPath) throws IOException {
        Map<String[], Map<String, String>> resultMap = new ConcurrentHashMap<>(50);

        String jsonString = readFromFile(jsonPath);
        if (!basePath.endsWith(File.separator)) {
            basePath = basePath + File.separator;
        }

        JSONObject jsonObject = JSONObject.parseObject(jsonString);


        Set<String> keySet = jsonObject.keySet();
        for (String s : keySet) {
            String filename = jsonObject.getJSONObject(s).get("filename").toString();
            Map<String, String> methodsMap = new HashMap<>();
            jsonObject.getJSONObject(s).getJSONObject("methods").getInnerMap().forEach((a, b) -> {
                methodsMap.put(a.substring(0, a.indexOf('(')), b.toString().substring(0, b.toString().indexOf('(')));
            });
            String originalFilename = filename.split("\\s")[0].trim();
            String targetFilename = filename.split("\\s")[1].trim();
            originalFilename = parseJavaFromSmali(basePath + sourcePkgName, originalFilename);
            targetFilename = parseJavaFromSmali(basePath + targetPkgName, targetFilename);
            System.out.println(originalFilename);
            System.out.println(targetFilename);
            System.out.println(methodsMap.toString());

            resultMap.put(new String[]{originalFilename, targetFilename}, methodsMap);
        }
        return resultMap;
    }

}
