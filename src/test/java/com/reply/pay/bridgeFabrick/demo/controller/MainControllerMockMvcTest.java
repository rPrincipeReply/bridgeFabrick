package com.reply.pay.bridgeFabrick.demo.controller;


import com.reply.pay.bridgeFabrick.demo.contract.CreateMoneyTransferContract;
import com.reply.pay.bridgeFabrick.demo.payload.CreateMoneyTransfer;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
//@RunWith(SpringRunner.class)
public class MainControllerMockMvcTest {
    private static final String ACCOUNT_ID_OK = "14537780";
    private static final String ACCOUNT_ID_KO = "9999999";

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;


    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void whenGetBalanceValidAccount_thenOk() throws Exception {
        final String url = "/bankAccount/" + ACCOUNT_ID_OK + "/balance";

        log.info("### Testing Endpoint: " + url);

        final MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").isNumber())
                .andReturn();

        log.info("### Stop Testing Endpoint: {} | Result: {}", url, result.getResponse().getContentAsString());
    }

    @Test
    public void whenGetBalanceNotValidAccount_then4xx() throws Exception {
        final String url = "/bankAccount/" + ACCOUNT_ID_KO + "/balance";

        log.info("### Testing Endpoint: " + url);

        final MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().is4xxClientError())
                .andReturn();

        log.info("### Stop Testing Endpoint: {} | Result: {}", url, result.getResponse().getContentAsString());
    }

    @Test
    public void whenGetTransactionsAccKoAndTimeFrame_then4xx() throws Exception {
        final String url = "/bankAccount/" + ACCOUNT_ID_KO + "/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-08-27";

        log.info("### Testing Endpoint: " + url);

        final MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().is4xxClientError())
                .andReturn();

        log.info("### Stop Testing Endpoint: {} | Result: {}", url, result.getResponse().getContentAsString());


    }

    @Test
    public void whenGetTransactionsAccOkAndTimeFrameKo_thenError() throws Exception {
        final String url = "/bankAccount/" + ACCOUNT_ID_OK + "/transactions?fromAccountingDate=BadFirstDate&toAccountingDate=2019-08-27";

        log.info("### Testing Endpoint: " + url);

        final MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().is4xxClientError())
                .andReturn();

        log.info("### Stop Testing Endpoint: {} | Result: {}", url, result.getResponse().getContentAsString());
    }

    @Test
    public void whenGetTransactionsAccOkAndTimeFrameOk_thenOk() throws Exception {
        final String url = "/bankAccount/" + ACCOUNT_ID_OK + "/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-08-27";

        log.info("### Testing Endpoint: " + url);

        final MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].valueDate").exists())
                .andExpect(jsonPath("$.[0].amount").isNumber())
                .andExpect(jsonPath("$.[1].valueDate").exists())
                .andExpect(jsonPath("$.[1].amount").isNumber())
                .andReturn();

        log.info("### Stop Testing Endpoint: {} | Result: {}", url, result.getResponse().getContentAsString());
    }

    @Test
    public void whenPostValidMoneyTransferAccKo_thenKO() throws Exception {
        final String url = "/bankAccount/" + ACCOUNT_ID_KO + "/payments/moneyTransfer";

        log.info("### Testing Endpoint: " + url);

        final CreateMoneyTransfer createMoneyTransfer = CreateMoneyTransferContract.newCreateMoneyTransferFromJson();

        final MvcResult result = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMoneyTransfer.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        log.info("### Stop Testing Endpoint: {} | Result: {}", url, result.getResponse().getContentAsString());
    }

    @Test
    public void whenPostValidMoneyTransfer_thenOK() throws Exception {
        final String url = "/bankAccount/" + ACCOUNT_ID_OK + "/payments/moneyTransfer";

        log.info("### Testing Endpoint: " + url);

        CreateMoneyTransfer createMoneyTransfer = CreateMoneyTransferContract.newCreateMoneyTransferFromJson();
        createMoneyTransfer.setExecutionDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        log.info("Posting Money Transfer: " + createMoneyTransfer);

        final MvcResult result = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createMoneyTransfer.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.balance").value(800))
                .andReturn();

        log.info("### Stop Testing Endpoint: {} | Result: {}", url, result.getResponse().getContentAsString());
    }
}