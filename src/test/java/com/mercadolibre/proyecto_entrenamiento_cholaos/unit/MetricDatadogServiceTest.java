package com.mercadolibre.proyecto_entrenamiento_cholaos.unit;

import com.mercadolibre.metrics.MetricCollector;
import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.config.metrics.MetricDatadogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MetricDatadogServiceTest {

    @Mock
    private MetricCollector metricCollector;

    @InjectMocks
    private MetricDatadogService metricDatadogService;

    @Captor
    private ArgumentCaptor<MetricCollector.Tags> tagsCaptor;

    @Test
    void incrementShouldCallMetricCollectorWithCorrectParameters() {
        String tag = "user";
        String tagValue = "user123";

        metricDatadogService.increment(tag, tagValue);

        verify(metricCollector).increment(eq(MetricDatadogService.METRIC_NAME), tagsCaptor.capture());
        assertNotNull(tagsCaptor.getValue());
    }
}