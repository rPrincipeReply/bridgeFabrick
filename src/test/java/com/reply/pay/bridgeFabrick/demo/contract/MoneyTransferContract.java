package com.reply.pay.bridgeFabrick.demo.contract;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.pay.bridgeFabrick.demo.payload.MoneyTransfer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MoneyTransferContract {

    @SneakyThrows
    public static MoneyTransfer newMoneyTransferFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = MoneyTransfer.class.getResourceAsStream("/moneyTransfer.json");
        return mapper.readValue(is, MoneyTransfer.class);
    }

}
