package at.wienerluft;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static String WR_API_URL = "https://www.wien.gv.at/ma22-lgb/umweltgut/lumesakt-v2.csv";

    private Scanner scanner;

    public static void main(String[] args) throws IOException {
        Main main = new Main();

        String msg = main.readFromWeb(WR_API_URL);
        WeatherFormatParser parser = new WeatherFormatParser(msg);
        HashMap<String, WeatherStationRecord> stationRecords = parser.parseMsg(msg);

        WeatherFormatParser.printStationRecords(stationRecords);
    }


    private String readFromWeb(String url) throws MalformedURLException, IOException {
        scanner = new Scanner(new URL(url).openStream(), "UTF-8");
        String msg = scanner.useDelimiter("\\A").next();
        scanner.close();

        return msg;
    }
}
