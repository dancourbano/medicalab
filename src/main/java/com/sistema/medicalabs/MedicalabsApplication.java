package com.sistema.medicalabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages="com.sistema.medicalabs")
public class MedicalabsApplication implements CommandLineRunner{
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(MedicalabsApplication.class, args);
		/*
		 * $2a$10$Th6CEQxf8T0KJR2ZNDuF8eoaudGecOAF1JOTMaZ6PE8DzgZi36gnm
$2a$10$4ZjN/CxWaTbvv/QtHiWRoun0ss3y3eLiYgkG3.frKyMtPvEeec2Qu
		 */
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		String password="12345";
		for(int i=0;i<2;i++) {
			String bcryptPassword=passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
		}
	}

}
