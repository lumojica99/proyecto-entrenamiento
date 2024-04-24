package com.mercadolibre.proyecto_entrenamiento_cholaos;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/** 
 * Main class for the App.
 */
@SpringBootApplication
public class Application {

  /** 
   * @param args command line arguments for the application.
   */
  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).registerShutdownHook(true).run(args);
  }

}
