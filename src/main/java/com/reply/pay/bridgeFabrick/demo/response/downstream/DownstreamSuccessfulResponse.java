package com.reply.pay.bridgeFabrick.demo.response.downstream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DownstreamSuccessfulResponse {
    private String status;
    private List<?> error;

}