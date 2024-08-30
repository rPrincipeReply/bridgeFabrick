package com.reply.pay.bridgeFabrick.demo.controller;


import com.reply.pay.bridgeFabrick.demo.payload.Balance;
import com.reply.pay.bridgeFabrick.demo.payload.CreateMoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.payload.MoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.payload.Transaction;
import com.reply.pay.bridgeFabrick.demo.service.MainService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Log4j2
@Controller
@RequestMapping("/bankAccount")
public class MainController {
    @Value("${spring.application.name}")
    String appName;

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Balance> balance(@PathVariable String accountId) {
        log.info("UpStream Request [GET][balance] accountId: {}", accountId);

        final Balance body = mainService.getBalance(accountId);

        log.info("UpStream Response [GET][balance] Body: {}", body);

        return new ResponseEntity<>(
                body,
                HttpStatus.OK);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<ArrayList<Transaction>> transactions(@PathVariable String accountId,
                                                               @RequestParam String fromAccountingDate,
                                                               @RequestParam String toAccountingDate) {
        log.info("UpStream Request [GET][transactions] accountId: {} | from: {} | to: {} ",
                accountId,
                fromAccountingDate,
                toAccountingDate);

                final ArrayList<Transaction> body = mainService.getTransactions(
                        accountId,
                        fromAccountingDate,
                        toAccountingDate);

        log.info("UpStream Response [GET][transactions] Body: {}", body);

        return new ResponseEntity<>(
                body,
                HttpStatus.OK);
    }


    @PostMapping(
            value = "/{accountId}/payments/moneyTransfer",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> moneyTransfer(@PathVariable String accountId,
                                           @RequestBody @Valid CreateMoneyTransfer createMoneyTransferPayload) {
        log.info("UpStream Request [POST][moneyTransfer] accountId: {} | payload: {}",
                accountId,
                createMoneyTransferPayload);

        final MoneyTransfer body = mainService.performMoneyTransfer(accountId, createMoneyTransferPayload);

        log.info("UpStream Response [POST][moneyTransfer] Body: {}", body);

        return new ResponseEntity<>(
                body,
                HttpStatus.OK);
    }
}