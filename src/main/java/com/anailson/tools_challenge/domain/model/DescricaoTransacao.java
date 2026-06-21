package com.anailson.tools_challenge.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DescricaoTransacao {

    private BigDecimal valor;

    private LocalDateTime dataHora;

    private String estabelecimento;

}
