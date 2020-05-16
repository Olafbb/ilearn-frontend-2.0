package com.ilearn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkDto implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("value")
    private Integer value;

    @JsonProperty("description")
    private String description;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("studentId")
    private Long studentId;

    public MarkDto() {
        super();
    }
}
