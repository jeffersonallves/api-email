package dev.jefferson.email.controllers;

import dev.jefferson.email.dtos.AuthenticationDTO;
import dev.jefferson.email.dtos.LoginResponseDTO;
import dev.jefferson.email.dtos.RegisterDTO;
import dev.jefferson.email.entities.User;
import dev.jefferson.email.repositories.UserRepository;
import dev.jefferson.email.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Login for access API", description = "Login API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged."),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Error when trying to log in")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary = "Register user for access API", description = "Register user for access API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered User."),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "500", description = "User registration error")
    })
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("Registered User.");
    }

    @Operation(summary = "Delete user", description = "This can only be done by the logged in user with role admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete User."),
            @ApiResponse(responseCode = "401", description = "Unauthorized user"),
            @ApiResponse(responseCode = "500", description = "User delete error")
    })
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

    @Operation(summary = "List all users", description = "List all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "User Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Users not found."),
            @ApiResponse(responseCode = "422", description = "Request data invalid"),
            @ApiResponse(responseCode = "500", description = "Error list users")
    })
    @GetMapping("/findAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }
}
