package com.reply.pay.bridgeFabrick.demo.response.downstream;

import com.reply.pay.bridgeFabrick.demo.payload.StandardStructure;
import com.reply.pay.bridgeFabrick.demo.payload.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DownstreamSuccessfulResponseTransactions extends DownstreamSuccessfulResponse {
    private StandardStructure<Transaction> payload;

}