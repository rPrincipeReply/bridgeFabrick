package com.reply.pay.bridgeFabrick.demo.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateMoneyTransfer {
    @Valid
    @NotNull(message = "creditor required")
    private Creditor creditor;
    @NotNull(message = "executionDate required")
    private String executionDate;
    private String uri;
    @NotEmpty(message = "description must be not empty")
    @NotNull(message = "description required")
    private String description;
    @NotNull(message = "amount required")
    @Min(value = 1, message = "amount must be greater than 1")
    private BigDecimal amount;
    @NotNull(message = "currency required")
    private String currency;
    private boolean isUrgent;
    private boolean isInstant;
    private String feeType;
    private String feeAccountId;
    @Valid
    @NotNull(message = "taxRelief required")
    private TaxRelief taxRelief;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Account {
        @Valid
        @NotNull(message = "creditor.account.accountCode required")
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
    private static class Creditor {
        @NotNull(message = "creditor.name required")
        private String name;
        @Valid
        @NotNull(message = "creditor.account required")
        private Account account;
        private Address address;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class LegalPersonBeneficiary {
        @NotNull(message = "taxRelief.beneficiaryType.legalPersonBeneficiary.fiscalCode required")
        private String fiscalCode;
        private String legalRepresentativeFiscalCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class NaturalPersonBeneficiary {
        @NotNull(message = "taxRelief.beneficiaryType.naturalPersonBeneficiary.fiscalCode1 required")
        private String fiscalCode1;
        private String fiscalCode2;
        private String fiscalCode3;
        private String fiscalCode4;
        private String fiscalCode5;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class TaxRelief {
        private String taxReliefId;
        @NotNull(message = "taxRelief.isCondoUpgrade required")
        private boolean isCondoUpgrade;
        @NotNull(message = "taxRelief.creditorFiscalCode required")
        private String creditorFiscalCode;
        @NotNull(message = "taxRelief.beneficiaryType required")
        private String beneficiaryType;
        @NotNull(message = "taxRelief.beneficiaryType.naturalPersonBeneficiary required")
        private NaturalPersonBeneficiary naturalPersonBeneficiary;
        @NotNull(message = "taxRelief.beneficiaryType.legalPersonBeneficiary required")
        private LegalPersonBeneficiary legalPersonBeneficiary;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(this);
    }
}
