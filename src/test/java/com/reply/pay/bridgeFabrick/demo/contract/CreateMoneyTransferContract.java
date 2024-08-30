package com.reply.pay.bridgeFabrick.demo.contract;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.pay.bridgeFabrick.demo.payload.CreateMoneyTransfer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreateMoneyTransferContract {

    @SneakyThrows
    public static CreateMoneyTransfer newCreateMoneyTransferFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = CreateMoneyTransfer.class.getResourceAsStream("/createMoneyTransfer.json");
        return mapper.readValue(is, CreateMoneyTransfer.class);
    }

}
