package at.wienerluft;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class WeatherFormatParser {
    private String msg;
    private HashMap<String, WeatherStationRecord> readings = new HashMap<String, WeatherStationRecord>();

    // Expected number of attributes - 26
    public WeatherFormatParser(String msg) {
        this.msg = msg;
    }

    public HashMap<String, String> getAsJSONPerStation() {
        HashMap<String, String> retVal = new HashMap<String, String>();

        for (String stationKey : readings.keySet()) {
            WeatherStationRecord station = readings.get(stationKey);
            String json = "{" + station.getJSON() + "}";
            retVal.put(station.getStationId(), json);
        }

        return retVal;
    }

    public String getJSON() {
        String json = "{";
        HashMap<String, String> jsonSt = this.getAsJSONPerStation();

        int i = 0;
        for (String stationKey : jsonSt.keySet()) {
            String stationJson = jsonSt.get(stationKey);

            if (i < jsonSt.size()-1 ) {
                json = json + stationJson + ", ";
            } else {
                json = json + stationJson;
            }
            i++;
        }

        return json + "}";
    }

    public HashMap<String, WeatherStationRecord> parseMsg(String msg) {
        BufferedReader reader = new BufferedReader(new StringReader(msg));

        HashMap<String, WeatherStationRecord> readingsList = new HashMap<String, WeatherStationRecord>();

        try {
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                // skip first 4 lines
                if (i >= 4) {
                    StringTokenizer strTok = new StringTokenizer(line, ";");

                    List<String> attrList = new ArrayList<String>();
                    while (strTok.hasMoreElements()) {
                        String attr = (String) strTok.nextElement();
                        attrList.add(attr);
                    }

                    if (attrList.size() != 26) {
                        System.out.println(
                                "Expected to have 26 attributes in data record - actual number was " + attrList.size());
                    } else {
                        // replace NE with empty string
                        for (int j = 0; j < attrList.size(); j++) {
                            if (attrList.get(j).equals("NE")) {
                                attrList.set(j, "");
                            }
                        }

                        // create record & extract attributes
                        WeatherStationRecord rec = new WeatherStationRecord();
                        rec.setRawMsg(line);
                        rec.setStationId(attrList.get(0));
                        rec.setDateTime(attrList.get(1));
                        rec.setWindSpeed(attrList.get(4));
                        rec.setWindDirection(attrList.get(5));
                        rec.setRf(attrList.get(7));
                        rec.setNo2(attrList.get(9));
                        rec.setNox(attrList.get(11));
                        rec.setPm10(attrList.get(13));
                        rec.setPm10_24(attrList.get(14));
                        rec.setPm25(attrList.get(16));
                        rec.setPm25_24(attrList.get(17));
                        rec.setO3(attrList.get(19));
                        rec.setO3_24(attrList.get(20));
                        rec.setS02(attrList.get(22));
                        rec.setCo(attrList.get(24));
                        rec.setCo_24(attrList.get(25));

                        readingsList.put(rec.getStationId(), rec);
                    }
                }

                i++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.readings = readingsList;
        return readingsList;
    }

    public static void printStationRecords(HashMap<String, WeatherStationRecord> stationRecords) {
        for (WeatherStationRecord rec : stationRecords.values()) {
            rec.printAsString(rec);
        }
    }

    public static void printStationRecordsFromJSON(HashMap<String, String> jsonList) {
        for (String json : jsonList.values()) {
            System.out.println(json);
        }
    }

}
