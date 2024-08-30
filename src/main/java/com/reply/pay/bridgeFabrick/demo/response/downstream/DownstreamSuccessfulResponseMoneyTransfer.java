package com.reply.pay.bridgeFabrick.demo.response.downstream;

import com.reply.pay.bridgeFabrick.demo.payload.MoneyTransfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DownstreamSuccessfulResponseMoneyTransfer extends DownstreamSuccessfulResponse {
    private MoneyTransfer payload;

}