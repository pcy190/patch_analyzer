import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by HAPPY
 */

public class main {

    private static int dumpLineLower = -4;
    private static int dumpLineUpper = 6;

    private static int countChar(String sentence, String targetChar) {
        int cnt = 0;
        int index = -1;
        while ((index = sentence.indexOf(targetChar, index)) > -1) {
            ++index;
            ++cnt;
        }
        return cnt;
    }

    private static String getStmt(String block, List<String> original, int line) {
        if (!block.contains("if"))
            return "";
        StringBuilder result = new StringBuilder();
        int numLeftBracket = 0;
        for (int i = line; i < original.size(); i++) {
            String curLine = original.get(i);
            if (0 == numLeftBracket && curLine.contains("{")) {
                numLeftBracket += countChar(curLine, "{");
                result.append(curLine.substring(curLine.indexOf("{")).trim()).append("\n");

            } else if (numLeftBracket != 0) {
                int cnt = countChar(curLine, "}");
                assert (cnt <= numLeftBracket);// no process to multi } in one line
                result.append(curLine.trim()).append("\n");
                numLeftBracket -= cnt;
                if (numLeftBracket == 0) break;
            }
        }

        return result.toString().trim();
    }

    static boolean isSameInstructions(String a, String b) {
        a = CodeParser.stripVariable(a);
        b = CodeParser.stripVariable(b);
        return a.trim().equals(b.trim());
    }

    static String searchSimilarBlock(String block, List<String> original, int line) {
        ArrayList<String> instructions = CodeParser.getInstructions(block);
        int offsetMax = min(line + instructions.size() * 20, original.size());
        CodeParser.stripTrivialInstructions(instructions);
        int offsetMin = max(line - instructions.size() * 20, 0);
        StringBuilder result = new StringBuilder();
        int score = 0;

        for (String instruction : instructions) {
            for (int i = offsetMin; i < offsetMax; i++) {
                if (isSameInstructions(instruction, original.get(i))) {
                    score++;
                    result.append(original.get(i).trim());
                }
            }
        }
        if (score > instructions.size() / 2)
            return result.toString();
        else {
            return "";
        }
    }

    private static boolean isUnaryCondition(String source, String target) {
        boolean isSourceCondition = source.contains(" if(");
        boolean isTargetCondition = target.contains(" if(");
        return isSourceCondition != isTargetCondition;
    }

//    static String replaceVariables(String sentence) {
//        Pattern p = Pattern.compile("v[\\d]+(_[\\d]+)");
//        Matcher m = p.matcher(sentence);
//        if (m.find()) {
//            String got = m.group();
//            got = got.replaceAll("(_[\\d]+)", "");
//            sentence = sentence.replaceAll(m.group(), got);
////             strict filter
//            sentence = sentence.replaceAll(m.group(), "");
//        }
//        return sentence;
////        return sentence.replaceAll("v[\\d]+(_[\\d]+)", "");
//    }

    private static String parseCondition(String sentence) {
        int leftBracketNum = 0;
        int startIdx = sentence.indexOf("if");
        if (startIdx == -1) {
            return "";
        }
        startIdx += 2;
        for (int i = startIdx; i < sentence.length(); i++) {
            if ('(' == sentence.charAt(i)) {
                leftBracketNum++;
            } else if (sentence.charAt(i) == ')') {
                leftBracketNum--;
                if (0 == leftBracketNum) {
                    return sentence.substring(startIdx + 1, i);
                }
            }
        }
        return "";
    }

    private static boolean isContraryCondition(String source, String target) {
        String convertedSource = source.replace("!=", "==");
        String convertedTarget = target.replace("!=", "==");
        return convertedSource.equals(convertedTarget);
    }


    private static void dumpDelta(AbstractDelta<String> delta, List<String> original, List<String> revised) {
        System.out.println(delta.getSource());
        System.out.println(delta.getTarget());
        System.out.println(delta.getType());
        System.out.println(delta.getSource().getPosition());
        System.out.println(delta.getTarget().getPosition());
        int sourceLine = delta.getSource().getPosition();
        int targetLine = delta.getTarget().getPosition();


        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
        dumpCodeContent(original, sourceLine, delta.getSource().getLines().size());

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        dumpCodeContent(revised, targetLine, delta.getTarget().getLines().size());
        System.out.println("\n");

    }

    private static void dumpCodeContent(List<String> original, int targetLine, int lineNum) {
        boolean hadLeftBrace = false;
        int lowerBound = max(0, targetLine + dumpLineLower);
        int upperBound = min(targetLine + dumpLineUpper + lineNum, original.size());
        for (int line = lowerBound; line < upperBound; ++line) {
            String lineToPrint = original.get(line);
            if (!hadLeftBrace) {
                if (lineToPrint.indexOf('{') == -1 && lineToPrint.indexOf('}') != -1) {
                    continue;
                } else if (lineToPrint.indexOf('{') != -1) {
                    hadLeftBrace = true;
                }
            }
            System.out.println(lineToPrint);
        }
    }

    private static int runNewConditionAnalyzer(AbstractDelta<String> delta, List<String> original, List<String> revised,
                                               String firstFile, String secondFile) {
        int targetCount = 0;
        if (!isUnaryCondition(delta.getSource().toString(), delta.getTarget().toString())) {
            return 0;
        }
        String sourceStmt = getStmt(delta.getSource().toString(), original, delta.getSource().getPosition());
        String targetStmt = getStmt(delta.getTarget().toString(), revised, delta.getTarget().getPosition());
        sourceStmt = CodeParser.getOuterBlock(sourceStmt);
        targetStmt = CodeParser.getOuterBlock(targetStmt);
        boolean found = false;
        if (!targetStmt.equals("")) {
            String another = searchSimilarBlock(targetStmt, original, delta.getSource().getPosition());
            if (!another.equals("")) {
                found = true;
            }
        } else if (!sourceStmt.equals("")) {
            String another = searchSimilarBlock(sourceStmt, revised, delta.getTarget().getPosition());
            if (!another.equals("")) {
                found = true;
            }
        }
        if (found) {
            ++targetCount;
            System.out.println("New Condition Case Found in \n\t" + firstFile + "\n\t" + secondFile);
            dumpDelta(delta, original, revised);
        }
        return targetCount;
    }

    private static int runSimilarConditionAnalyzer(AbstractDelta<String> delta, List<String> original, List<String> revised,
                                                   String firstFile, String secondFile) {
        int targetCount = 0;
        if (isUnaryCondition(delta.getSource().toString(), delta.getTarget().toString())) {
            return 0;
        }
        String source = delta.getSource().toString();
        String target = delta.getTarget().toString();

        String sourceCond = parseCondition(delta.getSource().toString()).trim();
        String targetCond = parseCondition(delta.getTarget().toString()).trim();
        if (sourceCond.equals("") || targetCond.equals("")) {
            return 0;
        }
        String sourceStmt = getStmt(source, original, delta.getSource().getPosition());
        String targetStmt = getStmt(target, revised, delta.getTarget().getPosition());
        if (Analyzer.valuateBlocks(sourceStmt, targetStmt) > 50) {
            int condScore = Analyzer.valuateCondition(sourceCond, targetCond);
            // expected not obfuscated condition
            if (condScore < 50) {
                ++targetCount;
                System.out.println("Similar Condition Found in the \n\t" + firstFile+"\n\t"+secondFile);
                dumpDelta(delta, original, revised);
            }
        }
        return targetCount;
    }

    private static int runNewPatchAnalyzer(String firstFile, String secondFile, Map<String, String> methodsMap) {
        int targetCount = 0;
        try {
            ArrayList<MethodDeclaration> sourceFunctionList = Analyzer.parseFunctionList(Paths.get(firstFile));
            ArrayList<MethodDeclaration> targetFunctionList = Analyzer.parseFunctionList(Paths.get(secondFile));


            assert sourceFunctionList != null;
            Map<MethodDeclaration, MethodDeclaration> obfMap = Analyzer.getSourceObfFunctionMap(sourceFunctionList, targetFunctionList);

            for (MethodDeclaration sourceDeclaration : obfMap.keySet()) {
                MethodDeclaration targetDeclaration = obfMap.get(sourceDeclaration);

                List<String> original = Arrays.asList(sourceDeclaration.getBody().toString().replace("\\r", "").split("\\n|\\r"));
                List<String> revised = Arrays.asList(targetDeclaration.getBody().toString().replace("\\r", "").split("\\n|\\r"));

                Patch<String> patch;
                patch = DiffUtils.diff(original, revised);
                for (AbstractDelta<String> delta : patch.getDeltas()) {
                    targetCount += runNewConditionAnalyzer(delta, original, revised, firstFile, secondFile);
                    targetCount += runSimilarConditionAnalyzer(delta, original, revised, firstFile, secondFile);
                }
            }

        } catch (DiffException e) {
            e.printStackTrace();
        }
        return targetCount;
    }

    /**
     * Main runner
     *
     * @param args
     */
    public static void main(String[] args) {
        /*
         * Process java file only
         *  Decompile the apk in advance.
         * */


        /**
         * Usage on linux:
         *  java -jar patch_analyser.jar /home/lhh/patch_if/analyzer/com.miui.securitycore2 com.miui.securitycore-22-22-9e86cfb9a036b4ce com.miui.securitycore-22-22-26a2b260d23cc05e /home/lhh/patch_if/jeb-pro-3.19.1.202005071620
         *
         *  java -jar patch_analyser.jar /home/lhh/patch_if/analyzer/com.xiaomi.misettings  com.xiaomi.misettings-2.8.5-200430011-24c9c68e67f1bfc4 com.xiaomi.misettings-2.7.2-200317011-2dad34ab84d36a3a /home/lhh/patch_if/jeb-pro-3.19.1.202005071620
         *
         *  java -jar patch_analyser.jar /home/lhh/patch_if/analyzer/com.miui.securitycore1 com.miui.securitycore-22-22-26a2b260d23cc05e com.miui.securitycore-22-22-2476fbb9d2b9bfbd /home/lhh/patch_if/jeb-pro-3.19.1.202005071620
         */

        if (args.length != 4) {
            System.out.println("Usage: commonPath sourcePkg targetPkg jebPath ");
            return;
        }
        String commonBase = args[0];
        String sourcePkgName = args[1];
        String targetPkgName = args[2];
        String jebPath = args[3];

        /**
         * format path
         */
        if (!commonBase.endsWith(File.separator)) {
            commonBase = commonBase + File.separator;
        }
        if (!jebPath.endsWith(File.separator)) {
            jebPath = jebPath + File.separator;
        }

        String jsonPath = commonBase + "similar.json";
//        String smaliDir = commonBase + "result\\similar";

        String sourceApkName = commonBase + sourcePkgName + ".apk";
        String targetApkName = commonBase + targetPkgName + ".apk";
        assert new File(sourceApkName).exists();
        assert new File(targetApkName).exists();
        Decompiler.decompileApk(sourceApkName, commonBase, jebPath);
        Decompiler.decompileApk(targetApkName, commonBase, jebPath);


        int targetCount = 0;
        try {
            Map<String[], Map<String, String>> infos = Utils.readJsonData(sourcePkgName, targetPkgName, commonBase, jsonPath);

            for (String[] fileList : infos.keySet()) {

                targetCount += runNewPatchAnalyzer(fileList[0], fileList[1], infos.get(fileList));
            }
            System.out.println(targetCount + " target patch found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
