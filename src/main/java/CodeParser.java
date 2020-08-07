import java.util.*;

/**
 * Created by HAPPY
 */


class CodeParser {

    private static String LEFT_BRACKET = "{";
    private static String RIGHT_BRACKET = "}";
    private static String[] trivialInstructions = {
            "if(",
            LEFT_BRACKET,
            RIGHT_BRACKET,

    };


    static String repalceNullToken(String sentence){
        if(sentence.endsWith(" null")){
            sentence = sentence.substring(0, sentence.lastIndexOf("null"))+"0";
        }
        sentence = sentence.replace("null)", "0)");
        sentence = sentence.replace(" null ", " 0 ");
        return sentence;
    }

    static String stripVariable(String sentence) {
        sentence = sentence.replaceAll("v[\\d]+(_[\\d]+)", "R");
        sentence = sentence.replaceAll("v[\\d]+", "R");
        sentence = sentence.replaceAll("arg[\\d]+", "R");
        return sentence;
    }

    static String getOuterBlock(String block) {
        int start = block.indexOf('{');
        int end = block.indexOf('}');
        if (start == -1) return block;
        if (end == -1) return block;
        return block.substring(start + 1, end).trim();
    }

    static boolean hasInnerBlock(String block) {
        return block.indexOf('{') != -1 && block.indexOf('}') != -1;
    }

    static ArrayList<String> getInstructions(String block) {
        String[] instructions = block.split("\\r?\\n");
        ArrayList<String> result = new ArrayList<>();
        for (String instruction : instructions) {
            instruction = instruction.trim();
            if (!instruction.equals("") && !instruction.equals("}")  && !instruction.contains("goto ")) {
                //&& !instruction.contains("return ")
                result.add(instruction);
            }
        }
        return result;
    }

    static ArrayList<String> stripTrivialInstructions(ArrayList<String> instructions) {
        Iterator<String> instIterator = instructions.iterator();
        while (instIterator.hasNext()) {
            String inst = instIterator.next();
            for (String trivial : trivialInstructions) {
                if (inst.contains(trivial)) {
                    instIterator.remove();
                    break;
                }
            }
        }
        return instructions;
    }


    static ArrayList<String> parseConditionToToken(String cond) {
        ArrayList<String> condList = new ArrayList<>();
        String[] tokens = cond.split("\\||&&");
        for (String token : tokens) {
            token = token.trim();
            if (!token.equals("")) {
                condList.add(token);
            }
        }
        return condList;
    }

    static ArrayList<String> splitToken(String tokenString) {
        ArrayList<String> condList = new ArrayList<>();
        String[] tokens = tokenString.split("\\.");
        for (String token : tokens) {
            token = token.trim();
            if (!token.equals("")) {
                condList.add(token);
            }
        }
        return condList;
    }
}
