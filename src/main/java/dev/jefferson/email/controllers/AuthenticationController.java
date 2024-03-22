package dev.jefferson.email.controllers;

import dev.jefferson.email.dtos.AuthenticationDTO;
import dev.jefferson.email.dtos.LoginResponseDTO;
import dev.jefferson.email.dtos.RegisterDTO;
import dev.jefferson.email.entities.User;
import dev.jefferson.email.repositories.UserRepository;
import dev.jefferson.email.security.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("Registered User.");
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity deleteUser(@PathVariable @NotBlank UUID userId) {
        if(userId == null) return ResponseEntity.badRequest().body("No username provided. Try again?");

        this.repository.deleteById(userId);

        final Optional<User> user = repository.findById(userId);

        if (null == user) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("User couldn't be deleted.");
        }
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }
}
