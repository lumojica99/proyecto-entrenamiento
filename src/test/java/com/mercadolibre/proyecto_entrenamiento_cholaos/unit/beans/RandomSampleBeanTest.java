package com.mercadolibre.proyecto_entrenamiento_cholaos.unit.beans;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mercadolibre.proyecto_entrenamiento_cholaos.infrastructure.config.beans.RandomSampleBean;
import com.mercadolibre.proyecto_entrenamiento_cholaos.application.dtos.SampleDTO;
import org.junit.jupiter.api.Test;

class RandomSampleBeanTest {

  @Test
  void randomPositiveTestOK() {
    RandomSampleBean randomSample = new RandomSampleBean();

    SampleDTO sample = randomSample.random();

    assertTrue(sample.getRandom() >= 0);
  }
}
