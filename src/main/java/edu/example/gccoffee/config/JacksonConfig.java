package edu.example.gccoffee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        //역직렬화 json->자바 객체
        //직렬화 자바 객체->json
        ObjectMapper mapper = new ObjectMapper();
        // Java 8 날짜/시간 모듈 등록
        mapper.registerModule(new JavaTimeModule());
        // write date as string
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
