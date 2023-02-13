package net.devdariobetances.kokoromachiaautheauthoservice.auth;

import lombok.RequiredArgsConstructor;
import net.devdariobetances.kokoromachiaautheauthoservice.config.JwtService;
import net.devdariobetances.kokoromachiaautheauthoservice.entity.Role;
import net.devdariobetances.kokoromachiaautheauthoservice.entity.User;
import net.devdariobetances.kokoromachiaautheauthoservice.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .pass(passwordEncoder.encode(request.getPass()))
                .role(Role.USER)
                .build();

        userRepo.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()
                )
        );

        var user = userRepo.findByPhone(request.getPhone())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}
