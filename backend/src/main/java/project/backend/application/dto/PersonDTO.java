package project.backend.application.dto;

import lombok.Data;

@Data
public class PersonDTO {
    private Long id;
    private String cpf;
    private String name;
    private String phone;
    private String username;
    private String password;
    private String role;
}
