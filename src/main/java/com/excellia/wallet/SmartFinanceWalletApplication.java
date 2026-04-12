package com.excellia.wallet;

import com.excellia.wallet.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmartFinanceWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartFinanceWalletApplication.class, args);
    }

    @Bean
    public CommandLineRunner initCategories(CategoryService categoryService) {
        return args -> categoryService.initSystemCategories();
    }
}