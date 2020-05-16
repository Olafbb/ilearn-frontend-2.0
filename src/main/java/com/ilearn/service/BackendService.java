package com.ilearn.service;

import com.ilearn.config.Links;
import com.ilearn.dto.*;
import com.ilearn.dto.ChatMessageDto;
import com.ilearn.dto.ChannelDto;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class BackendService {
    private Links links;
    private RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendService.class);

    public BackendService(Links links) {
        restTemplate = new RestTemplate();
        this.links = links;
    }

    public List<LessonDto> getLessons() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(links.getLessonsLink(), LessonDto[].class))));
    }

    public List<List<LessonDto>> getSchedule() {
        List<LessonDto> lessons = getLessons();
        List<LessonDto> monday = new ArrayList<>();
        List<LessonDto> tuesday = new ArrayList<>();
        List<LessonDto> wednesday = new ArrayList<>();
        List<LessonDto> thursday = new ArrayList<>();
        List<LessonDto> friday = new ArrayList<>();
        lessons.forEach(lessonDto -> {
            if (lessonDto.getDay().equals("MONDAY"))
                monday.add(lessonDto);
            if (lessonDto.getDay().equals("TUESDAY"))
                tuesday.add(lessonDto);
            if (lessonDto.getDay().equals("WEDNESDAY"))
                wednesday.add(lessonDto);
            if (lessonDto.getDay().equals("THURSDAY"))
                thursday.add(lessonDto);
            if (lessonDto.getDay().equals("FRIDAY"))
                friday.add(lessonDto);
        });
        List<List<LessonDto>> lessonsLists = new ArrayList<>();
        lessonsLists.add(monday);
        lessonsLists.add(tuesday);
        lessonsLists.add(wednesday);
        lessonsLists.add(thursday);
        lessonsLists.add(friday);
        lessonsLists.forEach(lessonList -> {
            lessonList.sort(Comparator.comparingInt(LessonDto::getLessonNr));
        });
        return lessonsLists;
    }

    public List<MarkDto> getMarks() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(links.getMarksLink(), MarkDto[].class))));
    }

    public List<HomeworkDto> getHomework() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(links.getHomeworkLink(), HomeworkDto[].class))));
    }

    public List<ChannelDto> getChannels() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(links.getChannelsLink(), ChannelDto[].class))));
    }

    public void uploadFile(SucceededEvent event, MemoryBuffer memoryBuffer) {
        File file = new File(event.getFileName());
        InputStream inputStream = memoryBuffer.getInputStream();
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("user-file", fileSystemResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        restTemplate.exchange(links.getUploadLink(), HttpMethod.POST, requestEntity, String.class);
        LOGGER.info("File: " + event.getFileName() + " was successfully uploaded");
    }

    public void editHomework(HomeworkDto homeworkDto) {
        restTemplate.put(links.getEditHomeworkLink(), homeworkDto);
    }

    public void createUser(UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userDtoJsonObject = new JSONObject();
        try {
            userDtoJsonObject.put("id", userDto.getId());
            userDtoJsonObject.put("email", userDto.getEmail());
            userDtoJsonObject.put("password", userDto.getPassword());
            userDtoJsonObject.put("studentId", userDto.getStudentId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity<UserDto> request = new HttpEntity<>(userDto, headers);
        restTemplate.postForObject(links.getCreateUserLink(), request, UserDto.class);
        LOGGER.info("Created new user: " + userDto.getEmail());
    }

    public List<UserDto> getUsers() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(links.getUsersLink(), UserDto[].class))));
    }

    public boolean checkStudentIdExist(Long id) {
        return restTemplate.getForObject(links.getStudentBasicLink() + "check/" + id, boolean.class);
    }

    public List<ChatMessageDto> getMessages() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(links.getMessagesLink(), ChatMessageDto[].class))));
    }

    public void createMessage(ChatMessageDto message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject messageJsonObject = new JSONObject();
        try {
            messageJsonObject.put("from", message.getFrom());
            messageJsonObject.put("time", message.getTime());
            messageJsonObject.put("message", message.getMessage());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity<ChatMessageDto> request = new HttpEntity<>(message, headers);
        restTemplate.postForObject(links.getCreateMessageLink(), request, ChatMessageDto.class);
    }

}
