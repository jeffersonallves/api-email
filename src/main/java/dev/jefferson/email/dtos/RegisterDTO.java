package dev.jefferson.email.dtos;

import dev.jefferson.email.enuns.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
