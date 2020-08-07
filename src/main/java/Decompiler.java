import java.io.*;

/**
 * Created by HAPPY
 */
public class Decompiler {
    public Decompiler() {
    }

    private static String getFileNameNoExt(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    static void decompileApk(String apkPath, String outputPath, String jebPath) {
        boolean isWindows = System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
        String cmd;

        ProcessBuilder processBuilder = new ProcessBuilder();

        if (isWindows) {
            String corePath = jebPath + "jeb_wincon.bat";
            String options = " -c --srv2 --script=scripts\\samples\\DecompileFile.py ";
            cmd = String.format("cmd.exe /c echo 123 & H: & cd %s & %s %s -- %s %s", jebPath, corePath, options, apkPath, outputPath);
            processBuilder.command(cmd.split("\\s"));

        } else {
            System.out.println("Running on linux.");
            String corePath = jebPath + "jeb_linux.sh";
            String options = String.format(" -c --srv2 --script=%sscripts/samples/DecompileFile.py ", jebPath);
            cmd = String.format("%s %s -- %s %s", corePath, options, apkPath, outputPath);
            processBuilder.command("sh","-c",cmd);

        }

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
        if (!outputPath.endsWith(File.separator)) {
            outputPath = outputPath + File.separator;
        }
        File output = new File(outputPath + "Bytecode_decompiled");
        output.renameTo(new File(outputPath + getFileNameNoExt(new File(apkPath).getName())));
    }

}
