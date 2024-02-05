package com.seojs.ptmanager.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.ptmanager.service.MemberService;
import com.seojs.ptmanager.web.dto.MemberDto;
import com.seojs.ptmanager.web.dto.MemberResponseDto;
import com.seojs.ptmanager.web.dto.MemberTicketDto;
import com.seojs.ptmanager.web.dto.TicketDto;
import org.assertj.core.api.Assertions;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TicketApiControllerTest {
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
        //등록
        String postUrl = "/api/v1/ticket";
        TicketDto ticketDto = new TicketDto(5, 10000);

        mvc.perform(post(postUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isOk());

        //조회
        String getUrl = "/api/v1/tickets";

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].totalNum", Matchers.is(5)))
                .andExpect(jsonPath("$[0].status", Matchers.is(true)))
                .andExpect(jsonPath("$[0].price", Matchers.is(10000)));
    }

    @Test
    void findById() throws Exception {
        //등록
        String postUrl = "/api/v1/ticket";
        TicketDto ticketDto = new TicketDto(5, 10000);

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isOk()).andReturn();

        Long id = extractedId(result);

        //조회
        String getUrl = "/api/v1/ticket/" + id;
        mvc.perform(get(getUrl))
                .andExpect(jsonPath("$.totalNum", Matchers.is(5)))
                .andExpect(jsonPath("$.status", Matchers.is(true)))
                .andExpect(jsonPath("$.price", Matchers.is(10000)));
    }

    @Test
    void findAll() throws Exception {
        //등록
        String postUrl = "/api/v1/ticket";
        TicketDto ticketDto1 = new TicketDto(5, 10000);
        TicketDto ticketDto2 = new TicketDto(10, 18000);
        TicketDto ticketDto3 = new TicketDto(15, 25000);

        mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto1)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto2)))
                .andExpect(status().isOk());

        mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto3)))
                .andExpect(status().isOk());

        //조회
        String getUrl = "/api/v1/tickets";

        mvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].totalNum", Matchers.is(5)))
                .andExpect(jsonPath("$[0].status", Matchers.is(true)))
                .andExpect(jsonPath("$[0].price", Matchers.is(10000)))
                .andExpect(jsonPath("$[1].totalNum", Matchers.is(10)))
                .andExpect(jsonPath("$[1].status", Matchers.is(true)))
                .andExpect(jsonPath("$[1].price", Matchers.is(18000)))
                .andExpect(jsonPath("$[2].totalNum", Matchers.is(15)))
                .andExpect(jsonPath("$[2].status", Matchers.is(true)))
                .andExpect(jsonPath("$[2].price", Matchers.is(25000)));
    }

    @Test
    void 티켓구매() throws Exception {
        //등록
        String postUrl = "/api/v1/ticket";
        TicketDto ticketDto = new TicketDto(5, 10000);

        MvcResult result = mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isOk()).andReturn();

        Long id = extractedId(result);

        //티켓 구매
        String patchUrl = "/api/v1/ticket/buy";
        MemberTicketDto memberTicketDto = new MemberTicketDto(memberId, id);

        mvc.perform(MockMvcRequestBuilders.patch(patchUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberTicketDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //회원 조회
        MemberResponseDto member = memberService.findById(memberId);
        Assertions.assertThat(member.getTickets().get(0).getTotalNum()).isEqualTo(5);
        Assertions.assertThat(member.getTickets().get(0).getStatus()).isEqualTo(true);
        Assertions.assertThat(member.getTickets().get(0).getPrice()).isEqualTo(10000);
    }

    private Long extractedId(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        MockHttpServletResponse response = result.getResponse();
        String responseBody = response.getContentAsString();
        return objectMapper.readValue(responseBody, Long.class);
    }
}