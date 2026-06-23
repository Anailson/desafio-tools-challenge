package com.anailson.tools_challenge.domain.model;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    private Long id;

    private Transacao transacao;;

    private FormaPagamento formaPagamento;
}
