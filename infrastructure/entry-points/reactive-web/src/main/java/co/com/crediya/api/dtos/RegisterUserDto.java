package co.com.crediya.api.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserDto {
    private String documentNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private Double baseSalary;
    private String roleName;
    private String password;
}
