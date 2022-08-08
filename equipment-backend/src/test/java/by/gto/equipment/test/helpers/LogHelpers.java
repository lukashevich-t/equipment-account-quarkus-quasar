package by.gto.equipment.test.helpers;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class LogHelpers {
    public static void writeCharSequenceToFile(CharSequence sb, String path) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(path, true);
             PrintWriter pw = new PrintWriter(fos)) {
            pw.print(sb);
        }
    }

    public static void writeObjectToFile(Object target, String path) throws Exception {
        if(target == null) {
            return;
        }
        writeCharSequenceToFile(target.toString(), path);
    }
}
