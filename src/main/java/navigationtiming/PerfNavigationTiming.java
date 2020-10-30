package navigationtiming;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static util.Constants.BROWSER_NAME;
import static util.Constants.PERF_METRICS_JSON;
import static util.WebDriverHolder.getDriver;

public class PerfNavigationTiming {

    Map<String, Object> timings = null;

    private static FileWriter fileWriter;

    private final String javaScriptForPerformance = "var performance = window.performance || window.webkitPerformance ||" +
            " window.mozPerformance || window.msPerformance || {};var timings = performance.timing || {};return timings;";
    private final String javaScriptForPerformanceInternetExplorer = "return {performance:JSON.stringify(performance.timing)}";

    public void writeToJson(String pageName) {
        getAllTiming();
        writeMetricsToJsonFile(pageName, this.getLatency(),
                this.getTimeToInteract(), this.getTimeToLoad(), this.getOnload(), this.getTotal_time());
        return;
    }


    public Map<String, Object> getAllTiming() {
        JavascriptExecutor jsrunner = (JavascriptExecutor) getDriver();
        if ("InternetExplorer".equalsIgnoreCase(BROWSER_NAME)) {
            Map<String, Object> ieTimings = (Map<String, Object>) jsrunner.executeScript(javaScriptForPerformanceInternetExplorer);
            timings = parseNavigationTimingDataFromIe(ieTimings);
        } else {
            timings = (Map<String, Object>) jsrunner.executeScript(javaScriptForPerformance);
        }
        return timings;
    }

    private Map<String, Object> parseNavigationTimingDataFromIe(Map<String, Object> ieTimings) {
        Map<String, Object> parsedTimings = new HashMap<>();
        String mapValue = (String) ieTimings.get("performance");
        mapValue = mapValue.substring(1, mapValue.length() - 1).replace("\"", "");
        String[] keyValuePairs = mapValue.split(",");

        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            parsedTimings.put(entry[0].trim(), Long.valueOf(entry[1]));
        }
        return parsedTimings;
    }

    @SneakyThrows
    private void writeMetricsToJsonFile(String pageName, long latency, long tti, long ttl, long onload, long totalTime) {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = null;
        try {
            jsonObject = PERF_METRICS_JSON.exists() ? mapper.readValue(PERF_METRICS_JSON, JSONObject.class) : new JSONObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray entityArray = new JSONArray();
        JSONObject innerJsonObject = new JSONObject();
        innerJsonObject.put("latency", latency);
        innerJsonObject.put("tti", tti);
        innerJsonObject.put("ttl", ttl);
        innerJsonObject.put("onload", onload);
        innerJsonObject.put("total_time", totalTime);

        entityArray.add(innerJsonObject);
        jsonObject.put(pageName, entityArray);

        try {
            fileWriter = new FileWriter(PERF_METRICS_JSON);
            fileWriter.write(jsonObject.toJSONString());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Long getAnTime(String name) {
        return (Long) timings.get((Object) name);
    }

    public Long getnavigationStart() {
        return getAnTime("navigationStart");
    }

    public Long getresponseStart() {
        return getAnTime("responseStart");
    }

    public Long getresponseEnd() {
        return getAnTime("responseEnd");
    }

    public Long getdomLoading() {
        return getAnTime("domLoading");
    }

    public Long getdomInteractive() {
        return getAnTime("domInteractive");
    }

    public Long getdomComplete() {
        return getAnTime("domComplete");
    }

    public Long getloadEventStart() {
        return getAnTime("loadEventStart");
    }

    public Long getloadEventEnd() {
        return getAnTime("loadEventEnd");
    }


    public long getLatency() {
        return getresponseStart() - getnavigationStart();
    }

    public long getBackend_response() {
        return getresponseEnd() - getresponseStart();
    }

    public long getTimeToInteract() {
        return getdomInteractive() - getdomLoading();
    }

    public long getTimeToLoad() {
        return getdomComplete() - getdomInteractive();
    }

    public long getOnload() {
        return getloadEventEnd() - getloadEventStart();
    }

    public long getTotal_time() {
        return getloadEventEnd() - getnavigationStart();
    }
}
