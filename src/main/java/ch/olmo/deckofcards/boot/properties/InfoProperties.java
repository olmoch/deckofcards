package ch.olmo.deckofcards.boot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("info.app")
public class InfoProperties {
  private String name;
  private String description;
  private String version;
}
