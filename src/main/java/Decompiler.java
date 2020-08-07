import java.io.*;

/**
 * Created by HAPPY on 2020/8/5 15:02
 */
public class Decompiler {
    public Decompiler() {
    }

    public static String getFileNameNoExt(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static boolean decompileApk(String apkPath, String outputPath, String jebPath) {
        boolean isWindows = System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
        String cmd;

        ProcessBuilder processBuilder = new ProcessBuilder();

        if (isWindows) {
            String corePath = jebPath + "jeb_wincon.bat";
            String options = " -c --srv2 --script=scripts\\samples\\DecompileFile.py ";
            /**
             * Change Driver Symbol in windows
             */
            cmd = String.format("cmd.exe /c echo 123 & H: & cd %s & %s %s -- %s %s", jebPath, corePath, options, apkPath, outputPath);
            processBuilder.command(cmd.split("\\s"));

        } else {
            System.out.println("Running on linux.");
            String corePath = jebPath + "jeb_linux.sh";
            String options = String.format(" -c --srv2 --script=%sscripts/samples/DecompileFile.py ", jebPath);
            cmd = String.format("%s %s -- %s %s", corePath, options, apkPath, outputPath);
            processBuilder.command("sh","-c",cmd);

        }

//        String cmd = String.format("cmd /c echo 123 && echo 321");
        System.out.println(cmd);


        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            Process p = Runtime.getRuntime().exec(cmd);
//            InputStream is = p.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            p.waitFor();
//            if (p.exitValue() != 0) {
//                System.out.println("Fail to Decompile!");
//                return false;
//            }
//            String s;
//            while (true) {
//                if ((s = reader.readLine()) == null) break;
//                System.out.println(s);
//            }
//            System.out.println("Decompile Successfully.");
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//            return false;
//        }
        if (!outputPath.endsWith(File.separator)) {
            outputPath = outputPath + File.separator;
        }
        File output = new File(outputPath + "Bytecode_decompiled");
        output.renameTo(new File(outputPath + getFileNameNoExt(new File(apkPath).getName())));
        return true;
    }

    public static void main(String[] args) {
//        decompileApk("D:\\WorkSpace\\SJTU\\com.android.bluetooth1\\com.android.bluetooth-10-29-0669f3926775aa3c.apk", "D:\\WorkSpace\\SJTU\\com.android.bluetooth1");
//        decompileApk("D:\\WorkSpace\\SJTU\\com.android.bluetooth1\\com.android.bluetooth-10-29-0669f3926775aa3c.apk", "D:\\WorkSpace\\SJTU\\com.android.bluetooth1");
    }
}
