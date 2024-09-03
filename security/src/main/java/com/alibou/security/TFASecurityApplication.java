package com.alibou.security;

import com.alibou.security.config.StorageService;
import com.alibou.security.user.Role;
import com.alibou.security.user.User;
import com.alibou.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//		(scanBasePackages = {"com.alibou.security.config", "com.alibou.security.user"})
@EnableJpaAuditing
@RequiredArgsConstructor
public class TFASecurityApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(TFASecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
//			User admin = User.builder()
//					.firstname("Admin")
//					.lastname("User")
//					.email("admin@gmail.com")
//					.password(passwordEncoder.encode("admin2024"))
//					.role(Role.ADMIN)
//					.mfaEnabled(false)
//					.build();
//
//			userRepository.save(admin);
//		}
	}
}