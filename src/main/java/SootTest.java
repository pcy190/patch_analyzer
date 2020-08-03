//import soot.Scene;
//import soot.SootClass;
//import soot.SootMethod;
//import soot.SourceLocator;
//import soot.options.Options;
//
//import java.io.File;
//
///**
// * Created by HAPPY on 2020/7/31
// */
//public class SootTest {
//    static String apiPath = "PATH\\com.xiaomi.bluetooth1\\com.xiaomi.bluetooth-10-29-1f791f81bfa7788d\\com\\android\\bluetooth\\ble\\app\\b";
//
//    public void getClassUnderDir() {
////        apiClasses = new LinkedHashSet<String>();
//
////        String[] sootParams = new String[] { "-cp", ".", "-pp", "-process-dir",  p.getProperty("cp"), "-d", p.getProperty("output"), "-v" };
//
////        soot.Main.main(sootParams);
//        for (String clzName : SourceLocator.v().getClassesUnder(apiPath)) {
//            System.out.printf("api class: %s\n", clzName);
//
//            Scene.v().loadClass(clzName, SootClass.BODIES).setApplicationClass();
//        }
//    }
//
//    public void getMethods() {
//        for (SootClass clz : Scene.v().getApplicationClasses()) {
//            System.out.println(clz.getName());
//            if (clz.getMethods().size() == 0) {
//                System.out.println("do not have methods!!!!!");
//            } else {
//                System.out.println("method num:" + clz.getMethods().size());
//                for (SootMethod me : clz.getMethods()) {
//                    System.out.println(me.toString());
//                    if (me.hasActiveBody()) {
//                        System.out.println(me.getActiveBody().toString());
//                    }
//                }
//            }
//
//        }
//
//    }
//
//    private static void setOptions() {
//        soot.options.Options.v().set_keep_line_number(true);
//        soot.options.Options.v().set_whole_program(true);
//        // LWG
//        soot.options.Options.v().setPhaseOption("jb", "use-original-names:true");
//        soot.options.Options.v().setPhaseOption("cg", "verbose:false");
//        soot.options.Options.v().setPhaseOption("cg", "trim-clinit:true");
//        //soot.options.Options.v().setPhaseOption("jb.tr", "ignore-wrong-staticness:true");
//
//        soot.options.Options.v().set_src_prec(Options.src_prec_java);
//        soot.options.Options.v().set_prepend_classpath(true);
//
//        // don't optimize the program
//        soot.options.Options.v().setPhaseOption("wjop", "enabled:false");
//        // allow for the absence of some classes
//        soot.options.Options.v().set_allow_phantom_refs(true);
//
//    }
//
//    private static void setSootClassPath() {
////        String JrePath = "F:\\Java\\jre\\lib\\";
//        String JrePath = "F:\\Java\\jdk7\\jre\\lib\\";
//        StringBuffer cp = new StringBuffer();
//        cp.append(".");
//        cp.append(File.pathSeparator).append(apiPath);
//
//        cp.append(File.pathSeparator)
//                .append(JrePath).append("rt.jar")
//                .append(File.pathSeparator)
//                .append(JrePath).append("jce.jar");
//        System.setProperty("soot.class.path", cp.toString());
//    }
//
////    public static void main(String[] args) {
////        setSootClassPath();
////        setOptions();
////
////        SootTest s = new SootTest();
////        s.getClassUnderDir();
////        s.getMethods();
////    }
//}
