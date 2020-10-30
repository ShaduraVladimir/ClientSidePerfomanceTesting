package util;

import lombok.SneakyThrows;
import pages.ComputersPage;
import pages.DesktopsPage;
import pages.HomePage;
import pages.ProductPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Constants {

    public final static String BROWSER_NAME = System.getProperty("browser");

    public final static String PERIODICITY = getProperties().get("periodicity");
    public final static String VERSION = getProperties().get("periodicity_comment");
    public final static String BUILD_ID = getProperties().get("buildID");

    public static String SCENARIO_NAME;
    public static String PROJECT_NAME = "RD_Demo";
    public final static String LOGIN_URL = getProperties().get("envUrl");
    public final static String ENV_NAME = "Test";

    public final static File PERF_METRICS_JSON = new File("perfMetrics.json");

    public final static BrowserFactory BROWSER_FACTORY = new BrowserFactory();

    public static HomePage HOME_PAGE;
    public static ComputersPage COMPUTERS_PAGE;
    public static DesktopsPage DESKTOPS_PAGE;
    public static ProductPage PRODUCT_PAGE;


    @SneakyThrows
    private static Map<String, String> getProperties() {
        Properties properties = new Properties();
        File propFile = new File("src/main/resources/application.properties");
        String propertiesFileName = propFile.getAbsolutePath();
        Map<String, String> propertiesMap = new HashMap<>();
        try {
            properties.load(new FileInputStream(propertiesFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        propertiesMap.put("envUrl", properties.getProperty("envUrl"));
        propertiesMap.put("dbUrl", properties.getProperty("dbUrl"));
        propertiesMap.put("periodicity", properties.getProperty("periodicity"));
        propertiesMap.put("periodicity_comment", properties.getProperty("periodicity_comment"));
        propertiesMap.put("buildID", properties.getProperty("buildID"));

        return propertiesMap;
    }

    public static void deleteJsonFiles() {
        if (PERF_METRICS_JSON.exists()) {
            PERF_METRICS_JSON.delete();
        }
    }

}
