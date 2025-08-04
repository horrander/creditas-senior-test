package br.com.creditas.simulador_emprestimo.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import br.com.creditas.simulador_emprestimo.dto.ErrorResponseDto;

class CustomExceptionHandlerTest {

    private CustomExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CustomExceptionHandler();
    }

    @Test
    void handleBusinessException_returnsBadRequestWithErrorResponse() {
        String errorMessage = "Business error occurred";
        BusinessException ex = new BusinessException(errorMessage);

        ResponseEntity<ErrorResponseDto> response = handler.handleBusinessException(ex);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMensagem()).isEqualTo(errorMessage);
        assertThat(response.getBody().getStatusCode()).isEqualTo(400);
    }

    @Test
    void handleTechinicalException_returnsInternalServerErrorWithGenericMessage() {
        Exception ex = new Exception("Some technical error");

        ResponseEntity<ErrorResponseDto> response = handler.handleTechinicalException(ex);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMensagem()).isEqualTo("Erro técnico inesperado");
        assertThat(response.getBody().getStatusCode()).isEqualTo(500);
    }
}
