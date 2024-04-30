package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.client;


import com.mercadolibre.proyecto_entrenamiento_cholaos.domain.exception.ItemNotFoundException;
import com.mercadolibre.restclient.MeliRestClient;
import com.mercadolibre.restclient.Response;
import com.mercadolibre.restclient.exception.RestException;
import com.mercadolibre.restclient.util.MeliContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Component
@RequiredArgsConstructor
public class KvsClient {

    private final MeliRestClient meliRestClient;
    private final MeliContext meliContext;

    public boolean isActive(String path) throws RestException {
        Response response = meliRestClient.get("https://internal-api.mercadolibre.com/"+path+"?attributes=status", meliContext);
        byte[] bytes = response.getBytes();
        String status = new String(bytes, StandardCharsets.UTF_8);
        if(status.contains("404")){
            throw new ItemNotFoundException("El objeto no se puede crear porque no existe en api interna");
        }
        return status.contains("active");
    }

}
