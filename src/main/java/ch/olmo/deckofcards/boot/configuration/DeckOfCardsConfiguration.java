package ch.olmo.deckofcards.boot.configuration;

import ch.olmo.deckofcards.boot.properties.InfoProperties;
import io.swagger.v3.oas.models.info.Info;
import java.security.SecureRandom;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeckOfCardsConfiguration {
  @Bean
  SecureRandom secureRandom() {
    return new SecureRandom();
  }

  @Bean
  public GroupedOpenApi openApi(InfoProperties infoProperties) {
    var info = new Info()
        .title(infoProperties.getName())
        .description(infoProperties.getDescription())
        .version(infoProperties.getVersion());
    return GroupedOpenApi.builder()
        .group("Poker")
        .addOpenApiCustomiser(openApi -> openApi.info(info))
        .build();
  }
}
