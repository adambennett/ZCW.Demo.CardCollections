package Collector.utilities;

public class FileSystem {

    public static String getResourcesPath() {
        return System.getProperty("user.dir") + fileSep() + "src" + fileSep() + "main" + fileSep() +"resources" + fileSep();
    }

    public static String fileSep() {
        return System.getProperty("file.separator");
    }

    public static Boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows");
    }
}
