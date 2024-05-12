package com.ssafy.algonote.note.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.algonote.exception.CustomException;
import com.ssafy.algonote.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {

    @Value("${gpt.api-key}")
    private String apiKey;

    @Value("${gpt.model}")
    private String model;

    private final RestTemplate restTemplate;

    public String getTimeComplexity(String userMsg) {
        String systemMsg = "Calculate the time complexity of the following Java code. Just give me the value, no explanation.";
        return callChatGpt(systemMsg, userMsg);
    }

    public String getSpaceComplexity(String userMsg) {
        String systemMsg = "Calculate the space complexity of the following Java code. Just give me the value, no explanation.";
        return callChatGpt(systemMsg, userMsg);
    }

    private String callChatGpt(String systemMsg, String userMsg) {
        final String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userMsg);
        messages.add(userMessage);

        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "system");
        assistantMessage.put("content", systemMsg);
        messages.add(assistantMessage);

        bodyMap.put("messages", messages);

        String body;
        try {
            body = objectMapper.writeValueAsString(bodyMap);
        } catch (JsonProcessingException e) {
            log.debug("JsonProcessingException : {}", e.getMessage());
            throw new CustomException(ErrorCode.JSON_GENERATING_ERROR);
        }

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            log.debug("JsonProcessingException : {}", e.getMessage());
            throw new CustomException(ErrorCode.JSON_PARSING_ERROR);
        }

        return jsonNode.path("choices").get(0).path("message").path("content").asText();
    }

}