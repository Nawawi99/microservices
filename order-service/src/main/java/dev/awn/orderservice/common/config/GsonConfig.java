package dev.awn.orderservice.common.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

    @Bean
    public Gson gson() {
        return new Gson().newBuilder()
                         .setPrettyPrinting()
                         .create();
    }

}
