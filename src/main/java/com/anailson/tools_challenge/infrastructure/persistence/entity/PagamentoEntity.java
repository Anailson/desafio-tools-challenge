package com.anailson.tools_challenge.infrastructure.persistence.entity;


import com.anailson.tools_challenge.domain.enums.StatusTransacao;
import com.anailson.tools_challenge.domain.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PAGAMENTO")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_TRANSACAO", nullable = false, unique = true)
    private String idTransacao;

    @Column(name = "CARTAO", nullable = false, length = 20)
    private String cartao;

    @Column(name = "VALOR", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "ESTABELECIMENTO", nullable = false)
    private String estabelecimento;

    @Column(name = "NSU")
    private String nsu;

    @Column(name = "CODIGO_AUTORIZACAO")
    private String codigoAutorizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusTransacao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_PAGAMENTO")
    private TipoPagamento tipoPagamento;

    @Column(name = "PARCELAS")
    private Integer parcelas;
}
