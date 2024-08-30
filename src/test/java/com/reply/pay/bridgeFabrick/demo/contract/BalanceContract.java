package com.reply.pay.bridgeFabrick.demo.contract;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.reply.pay.bridgeFabrick.demo.payload.Balance;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BalanceContract {

    @SneakyThrows
    public static Balance newBalanceFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Balance.class.getResourceAsStream("/balance.json");
        return mapper.readValue(is, Balance.class);
    }

}
