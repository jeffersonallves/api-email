package dev.jefferson.email.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class EmailDTO {

    @NotBlank(message = "The email cannot be empty")
    @Email(message = "The email must be valid")
    private String emailTo;
    private String emailCc;
    private String emailBcc;
    @NotBlank(message = "The subject cannot be empty")
    private String subject;
    private String text;
    private String templateName;
    private List<ContextVariableDTO> variables;
}
