package co.com.crediya.model.user.secrets;

//import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SecretManager {
//    @JsonProperty("JWT_SECRET")
    private String JWT_SECRET;

//    @JsonProperty("AWS_SQS_PUBLISH_REPORT_QUEUE_URL")
//    private String sqsPublishReportQueueUrl;
}
