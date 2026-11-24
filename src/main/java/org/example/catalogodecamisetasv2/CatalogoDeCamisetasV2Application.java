package org.example.catalogodecamisetasv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class CatalogoDeCamisetasV2Application {

    public static void main(String[] args) {
        SpringApplication.run(CatalogoDeCamisetasV2Application.class, args);
    }

}
