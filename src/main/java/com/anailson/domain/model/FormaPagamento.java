package com.anailson.domain.model;

import com.anailson.domain.enums.TipoPagamento;
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
