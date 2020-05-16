package com.ilearn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelDto {
    @JsonProperty("name")
    String name;
    @JsonProperty("numberOfPeople")
    Integer numberOfPeople;
    @JsonProperty("members")
    List<String> members;

    public ChannelDto() {
        super();
    }
}
