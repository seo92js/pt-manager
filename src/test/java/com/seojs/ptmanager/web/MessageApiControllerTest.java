package com.seojs.ptmanager.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.ptmanager.service.MemberService;
import com.seojs.ptmanager.service.TrainerService;
import com.seojs.ptmanager.web.dto.MemberDto;
import com.seojs.ptmanager.web.dto.MessageDto;
import com.seojs.ptmanager.web.dto.TrainerDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MessageApiControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    MemberService memberService;

    @Autowired
    TrainerService trainerService;

    @Autowired
    ObjectMapper objectMapper;

    Long memberId1;
    Long memberId2;
    Long trainerId1;
    Long trainerId2;

    @BeforeEach
    void setUp() {
        MemberDto memberDto1 = new MemberDto("id1", "name1", "password");
        memberId1 = memberService.save(memberDto1);

        MemberDto memberDto2 = new MemberDto("id2", "name2", "password");
        memberId2 = memberService.save(memberDto2);

        TrainerDto trainerDto1 = new TrainerDto("id1", "name2", "password");
        trainerId1 = trainerService.save(trainerDto1);

        TrainerDto trainerDto2 = new TrainerDto("id2", "name2", "password");
        trainerId2 = trainerService.save(trainerDto2);
    }

    @Test
    void save() throws Exception {
        //메시지 보내기
        String postUrl = "/api/v1/message";
        MessageDto messageDto1 = new MessageDto("멤버1->트레이너1", memberId1, trainerId1, null, null);
        MessageDto messageDto2 = new MessageDto("트레이너1->멤버1", null, null, trainerId1, memberId1);
        MessageDto messageDto3 = new MessageDto("멤버2->트레이너1", memberId2, trainerId1, null, null);

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto1)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto2)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto3)))
                .andExpect(status().isOk());

        //메시지 조회, memberId, trainerId로
        String getUrl = "/api/v1/message/" + memberId1 + "/" + trainerId1;
        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void 멤버ID로_보낸메시지_찾기() throws Exception {
        String postUrl = "/api/v1/message";
        MessageDto messageDto1 = new MessageDto("멤버1->트레이너1", memberId1, trainerId1, null, null);
        MessageDto messageDto2 = new MessageDto("트레이너1->멤버1", null, null, trainerId1, memberId1);
        MessageDto messageDto3 = new MessageDto("멤버2->트레이너2", memberId2, trainerId2, null, null);
        MessageDto messageDto4 = new MessageDto("멤버1->트레이너2", memberId1, trainerId2, null, null);

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto1)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto2)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto3)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto4)))
                .andExpect(status().isOk());

        //보낸 메시지 조회, memberId
        String getUrl = "/api/v1/message/send-member/" + memberId1;

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].content", Matchers.is("멤버1->트레이너1")))
                .andExpect(jsonPath("$[1].content", Matchers.is("멤버1->트레이너2")));
    }

    @Test
    void 멤버ID로_받은메시지_찾기() throws Exception {
        String postUrl = "/api/v1/message";
        MessageDto messageDto1 = new MessageDto("멤버1->트레이너1", memberId1, trainerId1, null, null);
        MessageDto messageDto2 = new MessageDto("트레이너1->멤버1", null, null, trainerId1, memberId1);
        MessageDto messageDto3 = new MessageDto("멤버2->트레이너2", memberId2, trainerId2, null, null);
        MessageDto messageDto4 = new MessageDto("트레이너2->멤버1", null, null, trainerId2, memberId1);

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto1)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto2)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto3)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto4)))
                .andExpect(status().isOk());

        //보낸 메시지 조회, memberId
        String getUrl = "/api/v1/message/receive-member/" + memberId1;

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].content", Matchers.is("트레이너1->멤버1")))
                .andExpect(jsonPath("$[1].content", Matchers.is("트레이너2->멤버1")));
    }

    @Test
    void 트레이너ID로_보낸메시지_찾기() throws Exception {
        String postUrl = "/api/v1/message";
        MessageDto messageDto1 = new MessageDto("멤버1->트레이너1", memberId1, trainerId1, null, null);
        MessageDto messageDto2 = new MessageDto("트레이너1->멤버1", null, null, trainerId1, memberId1);
        MessageDto messageDto3 = new MessageDto("멤버2->트레이너2", memberId2, trainerId2, null, null);
        MessageDto messageDto4 = new MessageDto("트레이너1->멤버2", null, null, trainerId1, memberId2);

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto1)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto2)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto3)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto4)))
                .andExpect(status().isOk());

        //보낸 메시지 조회, memberId
        String getUrl = "/api/v1/message/send-trainer/" + trainerId1;

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].content", Matchers.is("트레이너1->멤버1")))
                .andExpect(jsonPath("$[1].content", Matchers.is("트레이너1->멤버2")));
    }

    @Test
    void 트레이너ID로_받은메시지_찾기() throws Exception {
        String postUrl = "/api/v1/message";
        MessageDto messageDto1 = new MessageDto("멤버1->트레이너1", memberId1, trainerId1, null, null);
        MessageDto messageDto2 = new MessageDto("트레이너1->멤버1", null, null, trainerId1, memberId1);
        MessageDto messageDto3 = new MessageDto("멤버2->트레이너1", memberId2, trainerId1, null, null);
        MessageDto messageDto4 = new MessageDto("트레이너1->멤버2", null, null, trainerId1, memberId1);

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto1)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto2)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto3)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messageDto4)))
                .andExpect(status().isOk());

        //보낸 메시지 조회, memberId
        String getUrl = "/api/v1/message/receive-trainer/" + trainerId1;

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].content", Matchers.is("멤버1->트레이너1")))
                .andExpect(jsonPath("$[1].content", Matchers.is("멤버2->트레이너1")));
    }
}