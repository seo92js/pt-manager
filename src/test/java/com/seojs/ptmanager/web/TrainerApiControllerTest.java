package com.seojs.ptmanager.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.ptmanager.service.MemberService;
import com.seojs.ptmanager.web.dto.MemberDto;
import com.seojs.ptmanager.web.dto.TrainerDto;
import com.seojs.ptmanager.web.dto.TrainerTimeUpdateDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TrainerApiControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;

    Long memberId;

    @BeforeEach
    void setUp() {
        MemberDto memberDto = new MemberDto("id", "name", "password");
        memberId = memberService.save(memberDto);
    }

    @Test
    void save() throws Exception {
        //회원가입
        String postUrl = "/api/v1/trainer";
        TrainerDto trainerDto = new TrainerDto("idid", "name", "password");

        mvc.perform(post(postUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerDto)))
                .andExpect(status().isOk());

        //전체 조회
        String getUrl = "/api/v1/trainers";
        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("name")))
                .andExpect(jsonPath("$[0].loginId", Matchers.is("idid")));
    }

    @Test
    void findById() throws Exception {
        //회원가입
        String postUrl = "/api/v1/trainer";
        TrainerDto trainerDto = new TrainerDto("idid", "name", "password");

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerDto)))
                .andExpect(status().isOk())
                .andReturn();

        Long id = extractedId(result);
        //트레이너 조회
        LocalTime currentTime = LocalTime.now();

        String getUrl = "/api/v1/trainer/" + id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String min = LocalTime.MIN.truncatedTo(ChronoUnit.MINUTES).format(formatter);
        String max = LocalTime.MAX.truncatedTo(ChronoUnit.MINUTES).format(formatter);

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(id.intValue())))
                .andExpect(jsonPath("$.name", Matchers.is("name")))
                .andExpect(jsonPath("$.loginId", Matchers.is("idid")))
                .andExpect(jsonPath("$.startTime", Matchers.is(min)))
                .andExpect(jsonPath("$.endTime", Matchers.is(max)))
                .andExpect(jsonPath("$.sentMessages", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.receivedMessages", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.reserves", Matchers.hasSize(0)));
    }

    @Test
    void findByLoginId() throws Exception {
        //회원가입
        String postUrl = "/api/v1/trainer";
        String loginId = "idid";
        TrainerDto trainerDto = new TrainerDto(loginId, "name", "password");

        mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerDto)))
                .andExpect(status().isOk());

        //트레이너 조회
        String getUrl = "/api/v1/trainer/login-id/" + loginId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String min = LocalTime.MIN.truncatedTo(ChronoUnit.MINUTES).format(formatter);
        String max = LocalTime.MAX.truncatedTo(ChronoUnit.MINUTES).format(formatter);

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("name")))
                .andExpect(jsonPath("$.loginId", Matchers.is(loginId)))
                .andExpect(jsonPath("$.startTime", Matchers.is(min)))
                .andExpect(jsonPath("$.endTime", Matchers.is(max)))
                .andExpect(jsonPath("$.sentMessages", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.receivedMessages", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.reserves", Matchers.hasSize(0)));
    }

    @Test
    void findAll() throws Exception {
        //회원가입
        String postUrl = "/api/v1/trainer";
        TrainerDto trainerDto1 = new TrainerDto("id1", "name1", "password");
        TrainerDto trainerDto2 = new TrainerDto("id2", "name2", "password");
        TrainerDto trainerDto3 = new TrainerDto("id3", "name3", "password");

        mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerDto1)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerDto2)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerDto3)))
                .andExpect(status().isOk());

        //전체 조회
        String getUrl = "/api/v1/trainers";
        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].name", Matchers.is("name1")))
                .andExpect(jsonPath("$[0].loginId", Matchers.is("id1")))
                .andExpect(jsonPath("$[1].name", Matchers.is("name2")))
                .andExpect(jsonPath("$[1].loginId", Matchers.is("id2")))
                .andExpect(jsonPath("$[2].name", Matchers.is("name3")))
                .andExpect(jsonPath("$[2].loginId", Matchers.is("id3")));
    }

    @Test
    void 근무시간_변경() throws Exception {
        //회원가입
        String postUrl = "/api/v1/trainer";
        TrainerDto trainerDto1 = new TrainerDto("id1", "name1", "password");
        TrainerDto trainerDto2 = new TrainerDto("id2", "name2", "password");
        TrainerDto trainerDto3 = new TrainerDto("id3", "name3", "password");

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerDto1)))
                .andExpect(status().isOk())
                .andReturn();

        Long id = extractedId(result);

        //근무 시간 변경
        String patchUrl = "/api/v1/trainer/" + id + "/time";
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String now = localTime.truncatedTo(ChronoUnit.MINUTES).format(formatter);

        TrainerTimeUpdateDto trainerTimeUpdateDto = new TrainerTimeUpdateDto(localTime, localTime);

        mvc.perform(patch(patchUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerTimeUpdateDto)))
                .andExpect(status().isOk());

        //트레이너 조회
        String getUrl = "/api/v1/trainer/" + id;

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startTime", Matchers.is(now)))
                .andExpect(jsonPath("$.endTime", Matchers.is(now)));
    }

    private Long extractedId(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        MockHttpServletResponse response = result.getResponse();
        String responseBody = response.getContentAsString();
        return objectMapper.readValue(responseBody, Long.class);
    }
}