package at.wienerluft;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.events.cloud.pubsub.v1.Message;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TriggerPubSub implements BackgroundFunction<Message> {
    private static final Logger logger = Logger.getLogger(TriggerPubSub.class.getName());

    @Override
    public void accept(Message message, Context context) throws Exception {
        String bqProjectId = System.getenv("PROJECTID");
        String bqDataset = System.getenv("DATASET");
        String bqTable = System.getenv("BQTABLE");

        if (bqProjectId == null || bqDataset == null || bqTable == null) {
            throw new Exception("One of the environment variable is not set.");
        }

        logger.info(String.format("Starting projectId: %s bqDataset %s bqTable %s", bqProjectId, bqDataset, bqTable));
        Main main = new Main(bqProjectId, bqDataset, bqTable);
        return;

//        String name = "world";
//        if (message != null && message.getData() != null) {
//            name = new String(
//                    Base64.getDecoder().decode(message.getData().getBytes(StandardCharsets.UTF_8)),
//                    StandardCharsets.UTF_8);
//        }
//        logger.info(String.format("Hello %s!", name));

    }

}
