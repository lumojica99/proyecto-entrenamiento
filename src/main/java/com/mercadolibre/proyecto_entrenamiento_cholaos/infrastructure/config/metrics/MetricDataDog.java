package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.config.metrics;

public interface MetricDataDog {

    void increment(final String tag, final String tagValue);
}
