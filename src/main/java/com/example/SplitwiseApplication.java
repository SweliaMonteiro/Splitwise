package com.example;

import com.example.commands.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.Scanner;


@EnableJpaAuditing
@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {

    @Autowired
    private CommandExecutor commandExecutor;


    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                System.out.print("Enter command : ");
                String input = scanner.nextLine();
                commandExecutor.execute(input);
            }
            catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(SplitwiseApplication.class, args);
    }

}
