package br.com.cadeiralivreempresaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CadeiraLivreEmpresaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadeiraLivreEmpresaApiApplication.class, args);
    }
}
