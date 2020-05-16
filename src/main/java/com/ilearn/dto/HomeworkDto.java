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
public class HomeworkDto implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("deadline")
    private Date deadline;

    @JsonProperty("isDone")
    private Boolean isDone;

    @JsonProperty("studentId")
    private Long studentId;

    @JsonProperty("subject")
    private String subject;

    public HomeworkDto() {
        super();
    }
}
