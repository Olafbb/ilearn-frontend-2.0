package com.ilearn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeacherDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("lessons")
    private List<String> lessons;

    @JsonProperty("students")
    private List<String> students;

    @JsonProperty(value = "lessonsId", required = true)
    private List<Long> lessonsId;

    @JsonProperty(value = "studentsId", required = true)
    private List<Long> studentsId;

    public TeacherDto() {
        super();
    }
}
