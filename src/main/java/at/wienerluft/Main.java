package at.wienerluft;


import com.google.cloud.bigquery.*;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static String WR_API_URL = "https://www.wien.gv.at/ma22-lgb/umweltgut/lumesakt-v2.csv";

    private Scanner scanner;

    private String bqProjectId;
    private String bqDataset;
    private String bqTable;

    public static void main(String[] args) throws Exception {
        Options options = new Options();

        Option bqProjectIdOpt = new Option("bqProjectId", true, "BigQuery Project ID");
        bqProjectIdOpt.setRequired(true);
        options.addOption(bqProjectIdOpt);

        Option bqDatasetOpt = new Option("bqDataset", true, "BigQuery Dataset");
        bqProjectIdOpt.setRequired(true);
        options.addOption(bqDatasetOpt);

        Option bqTableOpt = new Option("bqTable", true, "BigQuery Table");
        bqProjectIdOpt.setRequired(true);
        options.addOption(bqTableOpt);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        Main main = new Main(cmd.getOptionValue("bqProjectId"), cmd.getOptionValue("bqDataset"), cmd.getOptionValue("bqTable"));
    }

    public Main(String bqProjectId, String bqDataset, String bqTable) throws Exception {
        this.bqProjectId = bqProjectId;
        this.bqDataset = bqDataset;
        this.bqTable = bqTable;


        String msg = readFromWeb(WR_API_URL);
        WeatherFormatParser parser = new WeatherFormatParser(msg);
        HashMap<String, WeatherStationRecord> stationRecords = parser.parseMsg(msg);

        // TODO: remove debug prints
        // WeatherFormatParser.printStationRecords(stationRecords);
        // System.out.println(parser.getAsBQInsertChain());

        writeToBQ(parser);

    }

    private String readFromWeb(String url) throws MalformedURLException, IOException {
        scanner = new Scanner(new URL(url).openStream(), "UTF-8");
        String msg = scanner.useDelimiter("\\A").next();
        scanner.close();

        return msg;
    }

    private void writeToBQ(WeatherFormatParser parser) throws Exception {
        String fqTable = String.format("%s.%s.%s", bqProjectId, bqDataset, bqTable);
        String attributes = "stationId, dateTime, windSpeed, windDirection, rf, no2, nox, pm10, pm10_24, pm25, pm25_24, o3, o3_24, S02,CO, CO_24";
        String insertSql = String.format("INSERT INTO `%s` (%s) VALUES %s", fqTable, attributes, parser.getAsBQInsertChain());

        System.out.println(insertSql);

        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(insertSql).build();

        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());
        queryJob = queryJob.waitFor();
        if (queryJob == null) {
            throw new Exception("job no longer exists");
        }
        // once the job is done, check if any error occured
        if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }



//        BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId(bqProjectId).build().getService();



//        final String INSERT_VEGETABLES =
//                "INSERT INTO `sample-project-330313.sample_dataset.vegetables` (id, name) VALUES (1, 'carrot'), (2, 'beans');";
//        QueryJobConfiguration queryConfig =
//                QueryJobConfiguration.newBuilder(INSERT_VEGETABLES).build();


//         Step 3: Run the job on BigQuery
//        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());
//        queryJob = queryJob.waitFor();
//        if (queryJob == null) {
//            throw new Exception("job no longer exists");
//        }
//        // once the job is done, check if any error occured
//        if (queryJob.getStatus().getError() != null) {
//            throw new Exception(queryJob.getStatus().getError().toString());
//        }
//
//        // Step 4: Display results
//        // Here, we will print the total number of rows that were inserted
//        JobStatistics.QueryStatistics stats = queryJob.getStatistics();

//        Long rowsInserted = stats.getDmlStats().getInsertedRowCount();
//        System.out.printf("%d rows inserted\n", stats.getNumDmlAffectedRows());
    }
}
