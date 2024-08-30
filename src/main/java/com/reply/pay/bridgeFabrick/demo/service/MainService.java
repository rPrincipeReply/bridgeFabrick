package com.reply.pay.bridgeFabrick.demo.service;

import com.reply.pay.bridgeFabrick.demo.payload.Balance;
import com.reply.pay.bridgeFabrick.demo.payload.CreateMoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.payload.MoneyTransfer;
import com.reply.pay.bridgeFabrick.demo.payload.Transaction;
import jakarta.validation.Valid;

import java.util.ArrayList;

public interface MainService {
    Balance getBalance(String accountId);

    ArrayList<Transaction> getTransactions(String accountId,
                                           String fromAccountingDate,
                                           String toAccountingDate);

    MoneyTransfer performMoneyTransfer(String accountId, @Valid CreateMoneyTransfer createMoneyTransferPayload);
}
