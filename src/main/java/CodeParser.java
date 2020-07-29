import java.util.ArrayList;

/**
 * Created by HAPPY
 */


public class CodeParser {
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
            if (!instruction.equals("") && !instruction.equals("}") && !instruction.contains("return ") && !instruction.contains("goto ")) {
                result.add(instruction);
            }
        }
        return result;
    }

    /**
     * @param source
     * @param target
     * @return similar score. From 0 to 100
     */
    static int valuateBlocks(String source, String target) {
        ArrayList<String> sourceInstructions = getInstructions(source);
        ArrayList<String> targetInstructions = getInstructions(target);

        int MAXSCORE = sourceInstructions.size();
        if(MAXSCORE==0){
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
        System.out.println("score: " + score);
        System.out.println("max score: " + MAXSCORE);
        return score * 100 / MAXSCORE;
    }

    /**
     * Only for test
     * @param args
     */
    public static void main(String[] args) {
        String source = "        Intent v0;\n" +
                "        String v1 = \"BluetoothOppLauncherActivity\";\n" +
                "        if(!BluetoothOppManager.getInstance(this.getApplicationContext()).isEnabled()) {\n" +
                "            if(BluetoothOppLauncherActivity.V) {\n" +
                "                Log.v(v1, \"Prepare Enable BT!! \");\n" +
                "            }\n" +
                "\n" +
                "            v0 = new Intent(((Context)this), BluetoothOppBtEnableActivity.class);\n" +
                "            v0.setFlags(0x10000000);\n" +
                "            this.startActivity(v0);\n" +
                "        }\n" +
                "        else {";
        String target = "        Intent v0;\n" +
                "        String v1 = \"BluetoothOppLauncherActivity\";\n" +
                "        if(!BluetoothOppManager.getInstance(((Context)this)).isEnabled()) {\n" +
                "            if(BluetoothOppLauncherActivity.V) {\n" +
                "                Log.v(v1, \"Prepare Enable BT!! \");\n" +
                "            }\n" +
                "\n" +
                "            v0 = new Intent(((Context)this), BluetoothOppBtEnableActivity.class);\n" +
                "            v0.setFlags(0x10000000);\n" +
                "            this.startActivity(v0);\n" +
                "        }\n" +
                "        else {";
//        System.out.println(getOuterBlock(""));
        System.out.println(valuateBlocks(source, target));
    }
}
