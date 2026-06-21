package com.anailson.tools_challenge.domain.model;

import com.anailson.tools_challenge.domain.enums.StatusTransacao;
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
