
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * Created by HAPPY on 2020/7/30
 */
public class Analyzer {

    private static final int EMPTY_FUNCTION_SCORE_THRESHOLD = 50;

    private static final boolean OBF_STRICT_SCHEME = false;

    static int valuateToken(String source, String target) {
        /**
         * Deal with the obf case
         */

        source = CodeParser.stripVariable(source);
        target = CodeParser.stripVariable(target);

        source = CodeParser.repalceNullToken(source);
        target = CodeParser.repalceNullToken(target);

        if (source.length() == target.length()) {
            if (source.length() == 0) return 100;
            boolean isObfuscated = true;
            for (int i = 0; i < source.length(); i++) {
                if (source.charAt(i) != target.charAt(i)) {
                    if (!(Character.isLetter(source.charAt(i)) && Character.isLetter(target.charAt(i)))) {
                        if (OBF_STRICT_SCHEME) {

                            if (abs(source.charAt(i) - target.charAt(i)) > 2) {
                                isObfuscated = false;
                            }
                        } else {
                            isObfuscated = false;

                        }
                    }

                }
            }
            if (isObfuscated) {
                return 100;
            }
        }

        ArrayList<String> sourceToken = CodeParser.splitToken(source);
        ArrayList<String> targetToken = CodeParser.splitToken(target);
        int hit = 0;
        if (sourceToken.size() != targetToken.size() || sourceToken.size() == 0) {
            return 0;
        } else {
            for (int i = 0; i < sourceToken.size(); i++) {
                if (!sourceToken.get(i).equals(targetToken.get(i))) {
                    if (CodeParser.stripVariable(sourceToken.get(i))
                            .equals(CodeParser.stripVariable(targetToken.get(i)))) {
                        hit++;
                    }
                } else {
                    hit++;
                }
            }
        }
        return hit * 100 / sourceToken.size();
    }

    /**
     * @param source
     * @param target
     * @return similar score. From 0 to 100.
     * @note if the block is empty, we assume that the similar score is 0.
     */
    static int valuateBlocks(String source, String target) {
        ArrayList<String> sourceInstructions = CodeParser.getInstructions(source);
        ArrayList<String> targetInstructions = CodeParser.getInstructions(target);

        int MAXSCORE = sourceInstructions.size();
        if (MAXSCORE == 0) {
            return 0;
        }
        int score = 0;
        for (String instruction : sourceInstructions) {
            for (int i = 0; i < targetInstructions.size(); i++) {
                String targetInstruction = targetInstructions.get(i);
                if (targetInstruction.equals(instruction)) {
                    ++score;
                    targetInstructions.remove(i);
                    break;
                }
            }
        }
//        System.out.println("score: " + score);
//        System.out.println("max score: " + MAXSCORE);
        return score * 100 / MAXSCORE;
    }

    static int valuateCondition(String sourceCond, String targetCond) {
        ArrayList<String> sourceCondTokenList = CodeParser.parseConditionToToken(sourceCond);
        ArrayList<String> targetCondTokenList = CodeParser.parseConditionToToken(targetCond);
        int hit = 0;
        if (sourceCondTokenList.size() != targetCondTokenList.size() || sourceCondTokenList.size() == 0) {
            return 0;
        }
        for (int i = 0; i < sourceCondTokenList.size(); i++) {
            if (valuateToken(sourceCondTokenList.get(i), targetCondTokenList.get(i)) == 100) {
                ++hit;
            }
        }

        return hit * 100 / sourceCondTokenList.size();
    }


    static int valuateFunctions(MethodDeclaration source, MethodDeclaration target) {
//        assert source.getBody().isPresent();
//        assert target.getBody().isPresent();

//        BlockStmt sourceBody = source.getBody().get();
//        BlockStmt targetBody = source.getBody().get();
        if (!source.getBody().isPresent() && !target.getBody().isPresent()) {
            return 100;
        } else if (!source.getBody().isPresent() || !target.getBody().isPresent()) {
            return EMPTY_FUNCTION_SCORE_THRESHOLD;
        }
        NodeList<Statement> sourceStmtList = source.getBody().get().getStatements();
        NodeList<Statement> targetStmtList = target.getBody().get().getStatements();
        ArrayList<String> sourceInstructions = sourceStmtList.stream().map(Node::toString).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> targetInstructions = targetStmtList.stream().map(Node::toString).collect(Collectors.toCollection(ArrayList::new));


        int score = 0;
        if (source.getDeclarationAsString().equals(target.getDeclarationAsString()))
            score += 50;
        else if (source.getNameAsString().equals(target.getNameAsString()))
            score += 30;

        int sourceInstSize = sourceInstructions.size();
        if (sourceInstSize == 0) {
            if (targetInstructions.size() == 0) return 100;
            else return 0;
        }
        sourceInstructions.removeAll(targetInstructions);
        score += 100 * (sourceInstSize - sourceInstructions.size()) / sourceInstSize;
        return score;
    }


    public static ArrayList<MethodDeclaration> parseFunctionList(Path path) {
        JavaParser javaParser = new JavaParser();
        Optional<CompilationUnit> op;
        try {
            op = javaParser.parse(path).getResult();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (!op.isPresent()) {
            System.out.println("Fail to parse");
            return null;
        }
        CompilationUnit cu = op.get();
        ArrayList<MethodDeclaration> funcList = new ArrayList<>();

        cu.accept(new VoidVisitorAdapter<ArrayList<MethodDeclaration>>() {
            @Override
            public void visit(MethodDeclaration n, ArrayList<MethodDeclaration> functionList) {
                String funcName = n.asMethodDeclaration().getNameAsString();
                String declare = n.getDeclarationAsString();
                functionList.add(n);
                if (declare.contains("Ua(String arg6)")) {

                    BlockStmt blockStmt = null;
                    try {
                        blockStmt = n.getBody().orElseThrow(() -> new AnalyzerException(""));
                    } catch (AnalyzerException e) {
                        e.printStackTrace();
                    }
                    assert blockStmt != null;
                    NodeList nodeList = blockStmt.getStatements();
                    for (int i = 0; i < nodeList.size(); i++) {
//                        if (nodeList.get(i) instanceof ExpressionStmt) {
//                            ExpressionStmt expressionStmt = (ExpressionStmt) nodeList.get(i);
////                            System.out.println(expressionStmt.toString());
////                            if (expressionStmt.getExpression() instanceof AssignExpr) {
////                                AssignExpr assignExpr = (AssignExpr) expressionStmt.getExpression();
////                                if (fieldList.get(j).getVariable(k).getNameAsString().equals(assignExpr.getTarget().toString())) {
////                                    setupFields.add(assignExpr.getTarget().toString());
////                                }
//                        } else if (nodeList.get(i) instanceof IfStmt) {
//                            IfStmt IfStmt = (IfStmt) nodeList.get(i);
//                            System.out.println(IfStmt.toString());
//                        }
                        if (nodeList.get(i) instanceof IfStmt) {
                            IfStmt IfStmt = (IfStmt) nodeList.get(i);
                            System.out.println(IfStmt.toString());
                        }
                    }
                }

//                System.out.println("Function:" + funcName + " " + declare);
            }

        }, funcList);

        return funcList;
    }


    public static String getFunctionBody(String funcName, ArrayList<MethodDeclaration> sourceFunctionList) {
        for (MethodDeclaration sourceDeclaration : sourceFunctionList) {
            /**
             * init() ignored
             */
            if (sourceDeclaration.getNameAsString().equals(funcName)) {
                return sourceDeclaration.getBody().toString();
            }
        }
        return "";

    }

    static Map<MethodDeclaration, MethodDeclaration> getSourceObfFunctionMap(ArrayList<MethodDeclaration> sourceFunctionList,
                                                                             ArrayList<MethodDeclaration> targetFunctionList) {
        Map<MethodDeclaration, MethodDeclaration> sourceObfFunctionMap = new HashMap<>();

        for (MethodDeclaration sourceDeclaration : sourceFunctionList) {
            int curMaxScore = 0;
            for (MethodDeclaration targetDeclaration : targetFunctionList) {
                int score = valuateFunctions(sourceDeclaration, targetDeclaration);
                if (score > 98) {
                    targetFunctionList.remove(targetDeclaration);
                    sourceObfFunctionMap.put(sourceDeclaration, targetDeclaration);
                    break;
                } else {
                    if (score > curMaxScore) {
                        sourceObfFunctionMap.put(sourceDeclaration, targetDeclaration);
                        curMaxScore = score;
                    }
                }
            }
//            }
        }
        return sourceObfFunctionMap;
    }

    public static void main(String[] args) throws IOException, AnalyzerException {
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        // with src/main/resources appended.
//        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class).resolve("src/main/resources"));
//        CompilationUnit cu = sourceRoot.parse("", "Blabla.java");

//        Expression expr = StaticJavaParser.parseExpression("v5.equals(g.FW)");

//        BlockStmt blockStmt = StaticJavaParser.parseBlock("v5.equals(g.FW)");
//        blockStmt.accept(new VoidVisitorAdapter<Void>(){
//
//            @Override
//            public void visit(ExpressionStmt n, Void arg) {
//
//            }
//        },null);


//        Log.info("Positivizing!");
//        CompilationUnit cu;
//        cu.accept(new ModifierVisitor<Void>() {
//            /**
//             * For every if-statement, see if it has a comparison using "!=".
//             * Change it to "==" and switch the "then" and "else" statements around.
//             */
//            @Override
//            public Visitable visit(IfStmt n, Void arg) {
//                // Figure out what to get and what to cast simply by looking at the AST in a debugger!
//                n.getCondition().ifBinaryExpr(binaryExpr -> {
//                    if (binaryExpr.getOperator() == BinaryExpr.Operator.NOT_EQUALS && n.getElseStmt().isPresent()) {
//                        /* It's a good idea to clone nodes that you move around.
//                            JavaParser (or you) might get confused about who their parent is!
//                        */
//                        Statement thenStmt = n.getThenStmt().clone();
//                        Statement elseStmt = n.getElseStmt().get().clone();
//                        n.setThenStmt(elseStmt);
//                        n.setElseStmt(thenStmt);
//                        binaryExpr.setOperator(BinaryExpr.Operator.EQUALS);
//                    }
//                });
//                return super.visit(n, arg);
//            }
//        }, null);
        String source = "0";
        File sourceFile = new File("D:\\WorkSpace\\SJTU\\com.xiaomi.bluetooth1\\com.xiaomi.bluetooth-10-29-1f791f81bfa7788d\\com\\android\\bluetooth\\ble\\app\\MiuiFastConnectService.java");
        File targetFile = new File("D:\\WorkSpace\\SJTU\\com.xiaomi.bluetooth1\\com.xiaomi.bluetooth-10-29-3cea60ddbf42344e\\com\\android\\bluetooth\\ble\\app\\MiuiFastConnectService.java");
        ArrayList<MethodDeclaration> sourceFunctionList = parseFunctionList(sourceFile.toPath());
        ArrayList<MethodDeclaration> targetFunctionList = parseFunctionList(targetFile.toPath());
//        ArrayList<MethodDeclaration> remainedTargetFunctionList = targetFunctionList.;
        Map<MethodDeclaration, MethodDeclaration> sourceObfFunctionMap = new HashMap<>();


        for (MethodDeclaration sourceDeclaration : sourceFunctionList) {
//            boolean found = false;
//            for (MethodDeclaration targetDeclaration : targetFunctionList) {
//                if (sourceDeclaration.getName().equals(targetDeclaration.getName())) {
//                    targetFunctionList.remove(targetDeclaration);
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                System.out.println(sourceDeclaration.getDeclarationAsString() + " not found, now try to search it.");
//            System.out.println("Searching " + sourceDeclaration.getNameAsString());
            int curMaxScore = 0;
            for (MethodDeclaration targetDeclaration : targetFunctionList) {
                int score = valuateFunctions(sourceDeclaration, targetDeclaration);
                if (score > 98) {
//                    System.out.println("Valute The target Function from "
//                            + sourceDeclaration.getDeclarationAsString()
//                            + " to "
//                            + targetDeclaration.getDeclarationAsString());
                    targetFunctionList.remove(targetDeclaration);
                    sourceObfFunctionMap.put(sourceDeclaration, targetDeclaration);
                    break;
                } else {
                    if (score > curMaxScore) {
                        sourceObfFunctionMap.put(sourceDeclaration, targetDeclaration);
                        curMaxScore = score;
                    }
                }
            }
//            }
        }
        sourceObfFunctionMap.forEach((sourceFunction, targetFunction) -> {
            System.out.println("Valute The target Function from "
                    + sourceFunction.getDeclarationAsString()
                    + " to "
                    + targetFunction.getDeclarationAsString());
        });
        System.out.println("Found " + sourceObfFunctionMap.size() + "/" + sourceFunctionList.size() + " mapped functions");
    }

}
