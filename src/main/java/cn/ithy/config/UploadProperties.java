package cn.ithy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "ccshop.upload")
public class UploadProperties {
    private String baseUrl;
    private List<String> AllowTypes;
}
