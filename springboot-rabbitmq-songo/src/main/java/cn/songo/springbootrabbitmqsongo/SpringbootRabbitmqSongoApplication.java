package cn.songo.springbootrabbitmqsongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootRabbitmqSongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRabbitmqSongoApplication.class, args);
	}

}
