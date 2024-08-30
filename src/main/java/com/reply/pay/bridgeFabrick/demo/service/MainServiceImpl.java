package com.reply.pay.bridgeFabrick.demo.service;


import com.reply.pay.bridgeFabrick.demo.payload.Balance;
import com.reply.pay.bridgeFabrick.demo.payload.CreateMoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.payload.MoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.payload.Transaction;
import com.reply.pay.bridgeFabrick.demo.response.downstream.DownstreamSuccessfulResponseBalance;
import com.reply.pay.bridgeFabrick.demo.response.downstream.DownstreamSuccessfulResponseMoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.response.downstream.DownstreamSuccessfulResponseTransactions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.GenericValidator;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final RestTemplate restTemplateFabrick;

    @Override
    public Balance getBalance(String accountId) {
        log.info("Entering Balance Service for account ID {}", accountId);

        if (!GenericValidator.isLong(accountId)) {
            throw new IllegalArgumentException("AccountId is not valid");
        }

        final String path = "/" + accountId + "/balance";

        log.info("DownStream Request [GET] to {}", path);

        final ResponseEntity<DownstreamSuccessfulResponseBalance> responseEntity =
                restTemplateFabrick.exchange(
                        path,
                        HttpMethod.GET,
                        null,
                        DownstreamSuccessfulResponseBalance.class);

        log.info("DownStream Response [{}] {}",
                responseEntity.getStatusCode(),
                Objects.requireNonNull(responseEntity.getBody()).getPayload());

        return Objects.requireNonNull(responseEntity.getBody()).getPayload();
    }

    @Override
    public ArrayList<Transaction> getTransactions(String accountId,
                                                  String fromAccountingDate,
                                                  String toAccountingDate) {

        log.info("Entering Transactions Service for account ID {}", accountId);

        if (!GenericValidator.isLong(accountId)) {
            throw new IllegalArgumentException("AccountId is not valid");
        }

        final LocalDate fromDate = LocalDate.parse(fromAccountingDate);
        final LocalDate toDate = LocalDate.parse(toAccountingDate);

        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("fromAccountingDate greater than toAccountingDate");
        }

        final String path = "/" + accountId + "/" + "transactions?"
                + "fromAccountingDate=" + fromAccountingDate
                + "&toAccountingDate=" + toAccountingDate;

        log.info("DownStream Request [GET] to {}", path);

        final ResponseEntity<DownstreamSuccessfulResponseTransactions> responseEntity =
                restTemplateFabrick.exchange(
                        path,
                        HttpMethod.GET,
                        null,
                        DownstreamSuccessfulResponseTransactions.class);

        log.info("DownStream Response [{}] {}",
                responseEntity.getStatusCode(),
                Objects.requireNonNull(responseEntity.getBody()).getPayload());

        return Objects.requireNonNull(responseEntity.getBody()).getPayload().getList();
    }

    @Override
    public MoneyTransfer performMoneyTransfer(String accountId, @Valid CreateMoneyTransfer createMoneyTransferPayload) {
        log.info("Entering Money Transfer Service for account ID {}", accountId);

        if (!GenericValidator.isLong(accountId)) {
            throw new IllegalArgumentException("AccountId is not valid");
        }

        final String path = "/" + accountId + "/payments/money-transfers";

        log.info("DownStream Request [POST] to {} | Body {}", path, createMoneyTransferPayload);

        final RequestEntity<CreateMoneyTransfer> requestEntity =
                RequestEntity
                        .post(path)
                        .accept(MediaType.ALL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(createMoneyTransferPayload);

        final ResponseEntity<DownstreamSuccessfulResponseMoneyTransfer> responseEntity =
                restTemplateFabrick.exchange(
                        requestEntity,
                        DownstreamSuccessfulResponseMoneyTransfer.class);

        log.info("DownStream Response [{}] {}",
                responseEntity.getStatusCode(),
                Objects.requireNonNull(responseEntity.getBody()).getPayload());

        return Objects.requireNonNull(responseEntity.getBody()).getPayload();
    }
}
