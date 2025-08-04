package br.com.creditas.simulador_emprestimo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    @Schema(description = "Mensagem de erro", example = "Erro ao processar a simulação de empréstimo")
    private String mensagem;

    @Schema(description = "Código de erro HTTP", example = "400, 500")
    private int statusCode;
}
