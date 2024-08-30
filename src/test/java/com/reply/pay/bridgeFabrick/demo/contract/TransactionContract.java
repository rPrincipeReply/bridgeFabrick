package com.reply.pay.bridgeFabrick.demo.contract;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.pay.bridgeFabrick.demo.payload.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionContract {

    @SneakyThrows
    public static ArrayList<Transaction> newTransactionsListFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Transaction.class.getResourceAsStream("/transactions.json");
        return mapper.readValue(is, new TypeReference<ArrayList<Transaction>>() {});
    }
}
