package at.wienerluft;


public class WeatherStationRecord {
    private String rawMsg;

    private String stationId;
    private String dateTime;
    private String windSpeed;
    private String windDirection;
    private String rf;
    private String no2;
    private String nox;
    private String pm10;
    private String pm10_24; // 24 mittelwert
    private String pm25;
    private String pm25_24; // 24 mittelwert
    private String o3;
    private String o3_24;
    private String s02;
    private String co;
    private String co_24;


    public WeatherStationRecord() {

    }

    public String getAsCSV() {
        String csv = String.format("('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                getStationId(),
                getDateTime(),
                getWindSpeed(),
                getWindDirection(),
                getRf(),
                getNo2(),
                getNox(),
                getPm10(),
                getPm10_24(),
                getPm25(),
                getPm25_24(),
                getO3(),
                getO3_24(),
                getS02(),
                getCo(),
                getCo_24()
        );

        return csv;
    }

    public static void printAsString(WeatherStationRecord rec) {
        System.out.println("------------------------------------------------");
        System.out.println("stationId: " + rec.getStationId());
        System.out.println("dateTime: " + rec.getDateTime());
        System.out.println("windSpeed: " + rec.getWindSpeed());
        System.out.println("windDirection: " + rec.getWindDirection());
        System.out.println("rf: " + rec.getRf());
        System.out.println("no2: " + rec.getNo2());
        System.out.println("nox: " + rec.getNox());
        System.out.println("pm10: " + rec.getPm10());
        System.out.println("pm10_24: " + rec.getPm10_24());
        System.out.println("pm25: " + rec.getPm25());
        System.out.println("pm25_24: " + rec.getPm25_24());
        System.out.println("o3: " + rec.getO3());
        System.out.println("o3_24: " + rec.getO3_24());
        System.out.println("S02: " + rec.getS02());
        System.out.println("CO: " + rec.getCo());
        System.out.println("CO_24: " + rec.getCo_24());
    }

    public String getJSON() {
        String json = "";
        json += "\"stationId\":\"" + getStationId() + "\",";
        json += "\"dateTime\":\"" + getDateTime() + "\",";
        json += "\"windSpeed\":\"" + getWindSpeed() + "\",";
        json += "\"windDirection\":\"" + getWindDirection() + "\",";
        json += "\"rf\":\"" + getRf() + "\",";
        json += "\"no2\":\"" + getNo2() + "\",";
        json += "\"nox\":\"" + getNox() + "\",";
        json += "\"pm10\":\"" + getPm10() + "\",";
        json += "\"pm10_24\":\"" + getPm10_24() + "\",";
        json += "\"pm25\":\"" + getPm25() + "\",";
        json += "\"pm25_24\":\"" + getPm25_24() + "\",";
        json += "\"o3\":\"" + getO3() + "\",";
        json += "\"o3_24\":\"" + getO3_24() + "\",";
        json += "\"S02\":\"" + getS02() + "\",";
        json += "\"CO\":\"" + getCo() + "\",";
        json += "\"CO_24\":\"" + getCo_24() + "\"";

        return json;
    }

    public String getRawMsg() {
        return rawMsg;
    }

    public void setRawMsg(String rawMsg) {
        this.rawMsg = rawMsg;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getRf() {
        return rf;
    }

    public void setRf(String rf) {
        this.rf = rf;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNox() {
        return nox;
    }

    public void setNox(String nox) {
        this.nox = nox;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm10_24() {
        return pm10_24;
    }

    public void setPm10_24(String pm10_24) {
        this.pm10_24 = pm10_24;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm25_24() {
        return pm25_24;
    }

    public void setPm25_24(String pm25_24) {
        this.pm25_24 = pm25_24;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO3_24() {
        return o3_24;
    }

    public void setO3_24(String o3_24) {
        this.o3_24 = o3_24;
    }

    public String getS02() {
        return s02;
    }

    public void setS02(String s02) {
        this.s02 = s02;
    }


    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCo_24() {
        return co_24;
    }

    public void setCo_24(String co_24) {
        this.co_24 = co_24;
    }





}

