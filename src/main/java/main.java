import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.min;

/**
 * Created by HAPPY
 */

class FolderFileScanner {

    private ArrayList<Object> scanFiles = new ArrayList<Object>();


    ArrayList<Object> scanFiles(String folderPath) throws IOException {
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            throw new IOException('"' + folderPath + '"' + " input path is not a directory.");
        }
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isDirectory()) {
                    scanFiles(file.getAbsolutePath());
                } else {
                    scanFiles.add(file.getAbsolutePath());
                }
            }
        }
        return scanFiles;
    }

}

public class main {

    private static int contraryConditionCnt = 0;
    private static int dumpLineLower = -2;
    private static int dumpLineUpper = 6;

    static String stripVariable(String sentence) {
        sentence = sentence.replaceAll("v[\\d]+(_[\\d]+)", "");
        sentence = sentence.replaceAll("v[\\d]+", "");
        sentence = sentence.replaceAll("arg[\\d]+", "");
        return sentence;
    }

    static int countChar(String sentence, String targetChar) {
        int cnt = 0;
        int index = -1;
        while ((index = sentence.indexOf(targetChar, index)) > -1) {
            ++index;
            ++cnt;
        }
        return cnt;
    }

    static String getStmt(String block, List<String> original, int line) {
        if (!block.contains("if("))
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
        a = stripVariable(a);
        b = stripVariable(b);
        return a.trim().equals(b.trim());
    }

    static String searchSimilarBlock(String block, List<String> original, int line) {
        ArrayList<String> instructions = CodeParser.getInstructions(block);
        int offsetMax = min(line + instructions.size() * 2, original.size());
        int offsetMin = line - 10;
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

    static boolean isUnaryCondition(String source, String target) {
        boolean isSourceCondition = source.contains(" if(");
        boolean isTargetCondition = target.contains(" if(");
        return isSourceCondition != isTargetCondition;
    }

    static String replaceVariables(String sentence) {
        Pattern p = Pattern.compile("v[\\d]+(_[\\d]+)");
        Matcher m = p.matcher(sentence);
        if (m.find()) {
            String got = m.group();
            got = got.replaceAll("(_[\\d]+)", "");
            sentence = sentence.replaceAll(m.group(), got);
//             strict filter
            sentence = sentence.replaceAll(m.group(), "");
        }
        return sentence;
//        return sentence.replaceAll("v[\\d]+(_[\\d]+)", "");
    }

    static String parseCondition(String sentence) {
//        Pattern p = Pattern.compile("if\\((.*)\\)");
//        Matcher m = p.matcher(sentence);
//        if (m.find()) {
//            return m.group();
//        }
//        return "";

        int leftBracketNum = 0;
        String condition = "";
        int startIdx = sentence.indexOf("if");
        if (startIdx == -1) {
            return "";
        }
        startIdx += 2;
        for (int i = startIdx; i < sentence.length(); i++) {
            if ('(' == sentence.charAt(i)) {
                leftBracketNum++;
                continue;
            } else if (sentence.charAt(i) == ')') {
                leftBracketNum--;
                if (0 == leftBracketNum) {
                    return sentence.substring(startIdx + 1, i);
                }
            }
        }
        return "";
    }

    static boolean isContraryCondition(String source, String target) {
        String convertedSource = source.replace("!=", "==");
        String convertedTarget = target.replace("!=", "==");
        return convertedSource.equals(convertedTarget);
    }

    static boolean isTargetDelta(AbstractDelta<String> delta) {
        if (delta.getSource().toString().contains(" if(") || delta.getTarget().toString().contains(" if(")) {
            String source = delta.getSource().toString();
            String target = delta.getTarget().toString();
//            source = replaceVariables(source);
            source = stripVariable(source);
//            target = replaceVariables(target);
            target = stripVariable(target);

            String sourceCond = parseCondition(source);
//            parser("private Uri creatFileForSharedContent(Context arg18, CharSequence arg19, String arg20) {\n" +
//                    "\tif(arg19 == null) {\n" +
//                    "\t\treturn null;\n" +
//                    "\t}\n" +
//                    "}");
            String targetCond = parseCondition(target);
            if (!sourceCond.equals(targetCond)) {

                if (isContraryCondition(sourceCond, targetCond)) {
                    ++contraryConditionCnt;
                    System.out.println("Found Similar Condition Delta   ===================");

                    return true;
                }
                return true;
            } else
                return false;
        }
        return false;
    }

    static void dumpDelta(AbstractDelta<String> delta, List<String> original, List<String> revised) {
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
        for (int line = targetLine + dumpLineLower; line < targetLine + dumpLineUpper + lineNum; ++line) {
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

    static int runNewConditionAnalyzer(AbstractDelta<String> delta, List<String> original, List<String> revised,
                                       String firstFile, String secondFile) {
        int targetCount = 0;
        if (!isUnaryCondition(delta.getSource().toString(), delta.getTarget().toString())) {
            return 0;
        }
        String sourceStmt = getStmt(delta.getSource().toString(), original, delta.getSource().getPosition());
        String targetStmt = getStmt(delta.getTarget().toString(), revised, delta.getTarget().getPosition());
        sourceStmt = CodeParser.getOuterBlock(sourceStmt);
        targetStmt = CodeParser.getOuterBlock(targetStmt);
        if (!targetStmt.equals("")) {
            String another = searchSimilarBlock(targetStmt, original, delta.getSource().getPosition());
            if (!another.equals("")) {

                ++targetCount;
                System.out.println(new File(firstFile).getName());
                dumpDelta(delta, original, revised);
            }
        } else if (!sourceStmt.equals("")) {
            String another = searchSimilarBlock(sourceStmt, revised, delta.getTarget().getPosition());
            if (!another.equals("")) {
                ++targetCount;
                System.out.println(new File(firstFile).getName());
                dumpDelta(delta, original, revised);
            }
        }
        return targetCount;
    }

    static int runSimilarConditionAnalyzer(AbstractDelta<String> delta, List<String> original, List<String> revised,
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
//        sourceStmt = CodeParser.getOuterBlock(sourceStmt);
//        targetStmt = CodeParser.getOuterBlock(targetStmt);
//        if (!targetStmt.equals("") && !sourceStmt.equals("")) {
        if (CodeParser.valuateBlocks(sourceStmt, targetStmt) > 50) {
            String another = searchSimilarBlock(targetStmt, original, delta.getSource().getPosition());
            if (!another.equals("")) {

                ++targetCount;
                System.out.println(new File(firstFile).getName());
                dumpDelta(delta, original, revised);
            }
        }
        return targetCount;
    }

    static int runPatchAnalyzer(String firstFile, String secondFile) {
        int targetCount = 0;
        try {
            List<String> original = Files.readAllLines(new File(firstFile).toPath());
            List<String> revised = Files.readAllLines(new File(secondFile).toPath());

            Patch<String> patch = null;
            patch = DiffUtils.diff(original, revised);
            for (AbstractDelta<String> delta : patch.getDeltas()) {
//                if (!isTargetDelta(delta)) {
//                    continue;
//                }
//                targetCount += runNewConditionAnalyzer(delta, original, revised, firstFile, secondFile);
                targetCount += runSimilarConditionAnalyzer(delta, original, revised, firstFile, secondFile);

            }
        } catch (DiffException | IOException e) {
            e.printStackTrace();
        }
        return targetCount;
    }

    static ArrayList<String> fetchFilenameFromSmali(String prefixDir, String smaliDir) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        if (!prefixDir.endsWith(File.separator)) {
            prefixDir = prefixDir + File.separator;
        }
        FolderFileScanner originalFileScanner = new FolderFileScanner();
        ArrayList<Object> originalFiles = originalFileScanner.scanFiles(smaliDir);
        for (Object originalFile : originalFiles) {
            String filename = new File(originalFile.toString().trim()).getName();
            if (filename.startsWith("1")) {
                filename = filename.substring(2);
                int dot = filename.lastIndexOf('.');

                if ((dot > -1) && (dot < (filename.length()))) {
                    filename = filename.substring(0, dot);
                }
                filename = filename.replace(".", File.separator);
                // replace the inner class
                filename = filename.replaceFirst("\\$[^.]+", "");
                filename = prefixDir + filename + ".java";

                if (new File(filename).exists()) {
                    result.add(filename);
                } else {
                    System.out.println("Can't found " + filename);
                    throw new IOException(filename + " source not found.");
                }

            }
        }
        return result;

    }


    /**
     * Main runner
     * @param args
     */
    public static void main(String[] args) {
        /*
         * Process java file only
         *  Decompile the apk first.
         * */

        String originalDir = "PATH\\com.android.updater-6.6.2-73-1f820e48253a818b";
        String revisedDir = "PATH\\com.android.updater-6.6.2-73-aef06b652ac468d0";
        String smaliDir = "PATH\\result\\similar";

        int targetCount = 0;
        try {
            /*FolderFileScanner originalFileScanner = new FolderFileScanner();
            ArrayList<Object> originalFiles = originalFileScanner.scanFiles(originalDir);
            System.out.println("Scan " + originalFiles.size() + " files totally in original directory.");
            FolderFileScanner revisedFileScanner = new FolderFileScanner();
            ArrayList<Object> revisedFiles = revisedFileScanner.scanFiles(revisedDir);
            System.out.println("Scan " + revisedFiles.size() + " files totally in revised directory.");*/


            int processedOriginalFiles = 0;

            ArrayList<String> originalFiles = fetchFilenameFromSmali(originalDir, smaliDir);
            ArrayList<String> revisedFiles = fetchFilenameFromSmali(revisedDir, smaliDir);

            for (Object originalFile : originalFiles) {
                String originalFilename = new File(originalFile.toString().trim()).getName();
                for (Object revisedFile : revisedFiles) {
                    String revisedFilename = new File(revisedFile.toString().trim()).getName();
                    if (originalFilename.equals(revisedFilename)) {
                        /* Run analyzer */
                        targetCount += runPatchAnalyzer(originalFile.toString(), revisedFile.toString());
                        processedOriginalFiles++;
                        revisedFiles.remove(revisedFile);
                        break;
                    }
                }
            }

            System.out.println((originalFiles.size() - processedOriginalFiles) + " original files remains.");
            System.out.println(revisedFiles.size() + " revised files remains.");
            System.out.println(targetCount + " target patch found.");
            System.out.println(contraryConditionCnt + " similar contrary condition patch found.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
