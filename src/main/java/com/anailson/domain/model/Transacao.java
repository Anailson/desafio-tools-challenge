package com.anailson.domain.model;

import com.anailson.domain.enums.StatusTransacao;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    private String id;

    private String cartao;

    private DescricaoTransacao descricao;

    private String nsu;

    private String codigoAutorizacao;

    private StatusTransacao status;

}
