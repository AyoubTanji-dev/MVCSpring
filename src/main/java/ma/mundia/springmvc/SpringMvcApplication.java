package ma.mundia.springmvc;

import ma.mundia.springmvc.entities.Patient;
import ma.mundia.springmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SpringMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(PatientRepository patientRepository){
		return args -> {
			patientRepository.save(new Patient(null, "Ayoub", new Date(), true, 180 ));
			patientRepository.save(new Patient(null, "Hassan", new Date(), false, 880 ));
			patientRepository.save(new Patient(null, "Ahmed", new Date(), true, 140));
			patientRepository.save(new Patient(null, "Youssef", new Date(), false, 300 ));

			patientRepository.findAll().forEach(p->{
				System.out.println(p.getName());
			});
		};

	}

}
