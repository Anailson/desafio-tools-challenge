package com.anailson.tools_challenge.domain.model;

import com.anailson.tools_challenge.domain.enums.TipoPagamento;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamento {

    private TipoPagamento tipo;

    private Integer parcelas;

}
