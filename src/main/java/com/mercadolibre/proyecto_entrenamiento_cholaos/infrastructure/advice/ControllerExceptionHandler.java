package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.advice;

import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.GenericException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ControllerExceptionHandler extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    Throwable throwable = super.getError(request);
    String mensaje = throwable.getMessage();
    if(throwable instanceof GenericException error){
      mensaje = error.getMessage();
      status = error.getStatus();
    }
    Map<String, Object> errorAttributes = new LinkedHashMap<>();
    errorAttributes.put("status", status.value());
    errorAttributes.put("mensaje", mensaje);
    return errorAttributes;
  }
}
