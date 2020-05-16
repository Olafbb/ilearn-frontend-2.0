package com.ilearn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonDto implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("day")
    private String day;

    @JsonProperty("lessonNr")
    private Integer lessonNr;

    @JsonProperty("link")
    private String link;

    @JsonProperty("classNumber")
    private String classNumber;

    @JsonProperty("students")
    private List<String> students;

    @JsonProperty("studentsId")
    private List<Long> studentsId;

    @JsonProperty("teachers")
    private List<String> teachers;

    @JsonProperty("teachersId")
    private List<Long> teachersId;

    public LessonDto() {
        super();
    }
}
