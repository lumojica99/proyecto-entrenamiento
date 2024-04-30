package com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.config.metrics;

import com.mercadolibre.metrics.MetricCollector;
import org.springframework.stereotype.Component;


@Component
public class MetricDatadogService implements MetricDataDog {

    public static final String METRIC_NAME = "rampup.onboarding.lumojica.cholaos";
    private final MetricCollector metricCollector;

    public MetricDatadogService(MetricCollector metricCollector) {
        this.metricCollector = metricCollector;
    }


    @Override
    public void increment(final String tag, final String tagValue){
        this.metricCollector.increment(METRIC_NAME, this.createTags(tag, tagValue));
    }

    private MetricCollector.Tags createTags(final String tag, final String tagValue){
        return new MetricCollector.Tags().add(tag, tagValue);
    }

}
