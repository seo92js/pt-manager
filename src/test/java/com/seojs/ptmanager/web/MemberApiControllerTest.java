package com.seojs.ptmanager.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.ptmanager.service.TicketService;
import com.seojs.ptmanager.service.TrainerService;
import com.seojs.ptmanager.web.dto.MemberDto;
import com.seojs.ptmanager.web.dto.TicketDto;
import com.seojs.ptmanager.web.dto.TrainerDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberApiControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TicketService ticketService;

    @Autowired
    TrainerService trainerService;

    Long trainerId;
    Long ticketId;

    @BeforeEach
    void setUp() {
        TrainerDto trainerDto = new TrainerDto("id", "name", "password");
        trainerId = trainerService.save(trainerDto);

        TicketDto ticketDto = new TicketDto(5, 10000);
        ticketId = ticketService.save(ticketDto);
    }

    @Test
    void save() throws Exception {
        //회원가입
        String postUrl = "/api/v1/member";
        MemberDto memberDto = new MemberDto("id", "name", "password");

        mvc.perform(MockMvcRequestBuilders.post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //전체 조회
        String getUrl = "/api/v1/members";

        mvc.perform(MockMvcRequestBuilders.get(getUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].loginId", Matchers.is("id")));
    }

    @Test
    void finyById() throws Exception {
        //회원가입
        String postUrl = "/api/v1/member";
        MemberDto memberDto = new MemberDto("id", "name", "password");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Long id = extractedId(result);

        String getUrl = "/api/v1/member/" + id;

        mvc.perform(MockMvcRequestBuilders.get(getUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sentMessages", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receivedMessages", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tickets", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reserves", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loginId", Matchers.is("id")));
    }

    @Test
    void findAll() throws Exception {
        //회원 가입
        String postUrl = "/api/v1/member";
        MemberDto memberDto1 = new MemberDto("id1", "name1", "password");
        MemberDto memberDto2 = new MemberDto("id2", "name2", "password");
        MemberDto memberDto3 = new MemberDto("id3", "name3", "password");

        mvc.perform(MockMvcRequestBuilders.post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto1)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto2)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto3)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //전체 조회
        String getUrl = "/api/v1/members";

        mvc.perform(MockMvcRequestBuilders.get(getUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("name1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].loginId", Matchers.is("id1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("name2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].loginId", Matchers.is("id2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("name3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].loginId", Matchers.is("id3")));
    }

    private Long extractedId(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        MockHttpServletResponse response = result.getResponse();
        String responseBody = response.getContentAsString();
        return objectMapper.readValue(responseBody, Long.class);
    }
}