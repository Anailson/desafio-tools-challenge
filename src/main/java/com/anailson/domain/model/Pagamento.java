package com.anailson.domain.model;


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
