package com.reply.pay.bridgeFabrick.demo.service;

import com.reply.pay.bridgeFabrick.demo.contract.BalanceContract;
import com.reply.pay.bridgeFabrick.demo.contract.CreateMoneyTransferContract;
import com.reply.pay.bridgeFabrick.demo.contract.MoneyTransferContract;
import com.reply.pay.bridgeFabrick.demo.contract.TransactionContract;
import com.reply.pay.bridgeFabrick.demo.payload.*;
import com.reply.pay.bridgeFabrick.demo.response.downstream.DownstreamSuccessfulResponseBalance;
import com.reply.pay.bridgeFabrick.demo.response.downstream.DownstreamSuccessfulResponseMoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.response.downstream.DownstreamSuccessfulResponseTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MainServiceImplTest {

    @InjectMocks
    private MainServiceImpl mainService;

    @Mock
    private RestTemplate restTemplateFabrick;

    @Mock
    private BodyBuilder bodyBuilder;

    @Test
    public void test_givenAccountId_whenGettingBalance_thenBalanceObjReturned() {

        final String accountId = "14537780";
        final Balance expectedBalance = BalanceContract.newBalanceFromJson();

        final DownstreamSuccessfulResponseBalance downstreamSuccessfulResponseBalance =
                new DownstreamSuccessfulResponseBalance(expectedBalance);

        final ResponseEntity<DownstreamSuccessfulResponseBalance> responseEntity =
                ResponseEntity.of(Optional.of(downstreamSuccessfulResponseBalance));

        final String path = "/" + accountId + "/balance";
        Mockito.when(restTemplateFabrick.exchange(
                        path,
                        HttpMethod.GET,
                        null,
                        DownstreamSuccessfulResponseBalance.class))
                .thenReturn(responseEntity);

        final Balance actualBalance = mainService.getBalance(accountId);

        Assertions.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void test_givenAccountIdNotValid_whenGettingBalance_thenIllegalArgumentException() {

        final String accountId = "notValidAccountId";

        Assertions.assertThrows(IllegalArgumentException.class, () -> mainService.getBalance(accountId));
    }

    @Test
    public void test_givenAccountIdAndTimeframe_whenGettingTransactions_thenTransactionsObjReturned() {

        final String accountId = "14537780";
        final ArrayList<Transaction> expectedTransactions = TransactionContract.newTransactionsListFromJson();

        final DownstreamSuccessfulResponseTransactions downstreamSuccessfulResponseTransactions =
                new DownstreamSuccessfulResponseTransactions(new StandardStructure<>(expectedTransactions));

        final ResponseEntity<DownstreamSuccessfulResponseTransactions> responseEntity =
                ResponseEntity.of(Optional.of(downstreamSuccessfulResponseTransactions));

        final String path = "/" + accountId + "/transactions?fromAccountingDate=2019-01-29&toAccountingDate=2019-02-28";
        Mockito.when(restTemplateFabrick.exchange(
                        path,
                        HttpMethod.GET,
                        null,
                        DownstreamSuccessfulResponseTransactions.class))
                .thenReturn(responseEntity);

        final ArrayList<Transaction> actualTransactions =
                mainService.getTransactions(
                        accountId,
                        "2019-01-29",
                        "2019-02-28");

        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void test_givenAccountIdAndNotValidFirstDate_whenGettingTransactions_thenDateTimeParseException() {

        final String accountId = "14537780";

        Assertions.assertThrows(
                DateTimeParseException.class,
                () -> mainService.getTransactions(
                        accountId,
                        "BadFirstDate",
                        "2019-02-28"));

    }

    @Test
    public void test_givenAccountIdAndNotValidSecondDate_whenGettingTransactions_thenDateTimeParseException() {

        final String accountId = "14537780";

        Assertions.assertThrows(
                DateTimeParseException.class,
                () -> mainService.getTransactions(
                        accountId,
                        "2019-01-29",
                        "BadSecondDate"));

    }

    @Test
    public void test_givenAccountIdAndNotValidTimeframe_whenGettingTransactions_thenIllegalArgumentException() {

        final String accountId = "14537780";

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> mainService.getTransactions(
                        accountId,
                        "2019-03-29",
                        "2019-02-28"));

    }

    @Test
    public void test_givenCreateMoneyTransferPayload_whenPostingMoneyTransfer_thenMoneyTransferObjReturned() {

        final String accountId = "14537780";
        final CreateMoneyTransfer createMoneyTransfer = CreateMoneyTransferContract.newCreateMoneyTransferFromJson();
        final MoneyTransfer expectedMoneyTransfer = MoneyTransferContract.newMoneyTransferFromJson();

        final DownstreamSuccessfulResponseMoneyTransfer downstreamSuccessfulResponseMoneyTransfer =
                new DownstreamSuccessfulResponseMoneyTransfer(expectedMoneyTransfer);

        final ResponseEntity<DownstreamSuccessfulResponseMoneyTransfer> responseEntity =
                ResponseEntity.of(Optional.of(downstreamSuccessfulResponseMoneyTransfer));

        final String path = "/" + accountId + "/payments/money-transfers";

        final RequestEntity<CreateMoneyTransfer> requestEntity =
                RequestEntity
                        .post(path)
                        .accept(MediaType.ALL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(createMoneyTransfer);

        Mockito.when(restTemplateFabrick.exchange(
                        requestEntity,
                        DownstreamSuccessfulResponseMoneyTransfer.class))
                .thenReturn(responseEntity);

        final MoneyTransfer actualMoneyTransfer = mainService.performMoneyTransfer(
                accountId,
                createMoneyTransfer);

        Assertions.assertEquals(expectedMoneyTransfer, actualMoneyTransfer);
    }

    @Test
    public void test_givenCreateMoneyTransferPayloadAndNotValidAccountId_whenPostingMoneyTransfer_thenIllegalArgumentException() {

        final String accountId = "notValidAccountId";
        final CreateMoneyTransfer createMoneyTransfer = CreateMoneyTransferContract.newCreateMoneyTransferFromJson();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> mainService.performMoneyTransfer(accountId, createMoneyTransfer));
    }
}
