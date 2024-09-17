package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.config;

import com.mercadolibre.kvsclient.ContainerKvsLowLevelClient;
import com.mercadolibre.kvsclient.kvsapi.KvsapiConfiguration;
import com.mercadolibre.kvsclient.kvsapi.KvsapiLowLevelClient;
import com.mercadolibre.metrics.MetricCollector;
import com.mercadolibre.metrics.datadog.DatadogFuryMetricCollector;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.client.KvsClient;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.output.kvs.database.KvsRepository;
import com.mercadolibre.restclient.MeliRESTPool;
import com.mercadolibre.restclient.MeliRestClient;
import com.mercadolibre.restclient.RESTPool;
import com.mercadolibre.restclient.RestClient;
import com.mercadolibre.restclient.util.MeliContext;
import com.mercadolibre.restclient.util.MeliContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BeanConfiguration {

    @Bean
    public MeliRestClient meliRestClient()  {
        RESTPool aPool = MeliRESTPool.builder()
                .withName("my_pool")
                .build();

        RESTPool bPool = MeliRESTPool.builder()
                .withName("my_bpool")
                .build();
        RestClient instance;
        try {
            instance = RestClient.builder()
                    .withPool(aPool, bPool)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new MeliRestClient(instance);
    }
    @Bean
    public MeliContext meliContext(){
        return MeliContextBuilder.buildFlowStarterContext();
    }
    @Bean
    public KvsClient itemKvsClient() {
        MeliRestClient meliRestClient = meliRestClient();
        MeliContext meliContext = meliContext();
        return new KvsClient(meliRestClient, meliContext);
    }

    @Bean ContainerKvsLowLevelClient kvs(){
        String containerName = System.getenv("KEY_VALUE_STORE_KVS_DEMO_CONTAINER_NAME");
        KvsapiConfiguration config = KvsapiConfiguration.builder()
                .withSocketTimeout(1000)
                .withMaxWait(100)
                .withConnectionTimeout(100)
                .withMaxConnections(30)
                .withMaxConnectionsPerRoute(30)
                .withMaxRetries(1)
                .withRetryDelay(30)
                .build();
        return new ContainerKvsLowLevelClient(new KvsapiLowLevelClient(config), containerName);
    }
    @Bean
    public KvsRepository kvsRepository(){
        ContainerKvsLowLevelClient kvs = kvs();
        return new KvsRepository(kvs);
    }

    @Bean
    public String itemPath(){
        return "items/";
    }

    @Bean
    public String userPath(){
        return "users/";
    }

    @Bean
    public MetricCollector metricCollector(){
        return DatadogFuryMetricCollector.INSTANCE;
    }


}
