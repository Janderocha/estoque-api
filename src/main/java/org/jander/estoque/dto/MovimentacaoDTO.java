package org.jander.estoque.dto;

import java.time.LocalDateTime;

public record MovimentacaoDTO(
    Long id,
    Long produtoId,
    String tipo,
    Integer quantidade,
    Double valor,
    LocalDateTime data
) {}