package com.anailson.tools_challenge.domain.model;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    private Transacao transacao;;

    private FormaPagamento formaPagamento;
}
