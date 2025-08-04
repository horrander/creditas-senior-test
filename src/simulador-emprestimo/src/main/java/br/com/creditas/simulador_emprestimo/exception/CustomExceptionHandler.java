package br.com.creditas.simulador_emprestimo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.creditas.simulador_emprestimo.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            BusinessException.class,
    })
    ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .mensagem(ex.getMessage())
                .statusCode(400)
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler({
            Exception.class
    })
    ResponseEntity<ErrorResponseDto> handleTechinicalException(Exception ex) {

        log.error("Erro técnico inesperado: {}", ex.getMessage(), ex);

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .mensagem("Erro técnico inesperado")
                .statusCode(500)
                .build();

        return ResponseEntity.status(500).body(errorResponse);
    }
}
