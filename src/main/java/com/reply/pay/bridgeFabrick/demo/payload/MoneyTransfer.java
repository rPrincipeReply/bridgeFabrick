package com.reply.pay.bridgeFabrick.demo.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoneyTransfer {
    private String moneyTransferId;
    private String status;
    private String direction;
    private Creditor creditor;
    private Debtor debtor;
    private String cro;
    private String uri;
    private String trn;
    private String description;
    private Date createdDatetime;
    private Date accountedDatetime;
    private String debtorValueDate;
    private String creditorValueDate;
    private Amount amount;
    private boolean isUrgent;
    private boolean isInstant;
    private String feeType;
    private String feeAccountId;
    private ArrayList<Fee> fees;
    private boolean hasTaxRelief;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Account {
        private String accountCode;
        private String bicCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Address {
        private Object address;
        private Object city;
        private Object countryCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Amount {
        private int debtorAmount;
        private String debtorCurrency;
        private int creditorAmount;
        private String creditorCurrency;
        private String creditorCurrencyDate;
        private int exchangeRate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Creditor {
        private String name;
        private Account account;
        private Address address;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Debtor {
        private String name;
        private Account account;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Fee {
        private String feeCode;
        private String description;
        private double amount;
        private String currency;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(this);
    }
}
