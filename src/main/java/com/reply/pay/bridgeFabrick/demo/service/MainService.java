package com.reply.pay.bridgeFabrick.demo.service;

import com.reply.pay.bridgeFabrick.demo.payload.Balance;
import com.reply.pay.bridgeFabrick.demo.payload.CreateMoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.payload.MoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.payload.Transaction;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;

public interface MainService {
    Balance getBalance(final String accountId);

    ArrayList<Transaction> getTransactions(final String accountId,
                                           final LocalDate fromAccountingDate,
                                           final LocalDate toAccountingDate);

    MoneyTransfer performMoneyTransfer(final String accountId, @Valid final CreateMoneyTransfer createMoneyTransferPayload);
}
