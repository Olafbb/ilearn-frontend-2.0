package com.ilearn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessageDto {
    @JsonProperty("from")
    private String from;
    @JsonProperty("time")
    private LocalDateTime time;
    @JsonProperty("message")
    private String message;

    public ChatMessageDto() {
        super();
    }
}
