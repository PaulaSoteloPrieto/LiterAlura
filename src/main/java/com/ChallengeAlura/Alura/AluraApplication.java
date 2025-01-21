package com.ChallengeAlura.Alura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluraApplication implements CommandLineRunner{
	@Autowired
	private final Main main;

    public AluraApplication(Main main) {
        this.main = main;
    }

    public static void main(String[] args) {
		SpringApplication.run(AluraApplication.class, args);
	}


	@Override
		public void run(String... args) throws Exception {
		main.muestraMenu();

}
}



