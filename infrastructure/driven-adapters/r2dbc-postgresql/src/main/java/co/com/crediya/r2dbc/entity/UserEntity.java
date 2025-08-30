package co.com.crediya.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("id_user")
    private Long id;

    @Column("document_number")
    private String documentNumber;

    @Column("name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("birthdate")
    private LocalDate birthDate;

    @Column("address")
    private String address;

    @Column("phone_number")
    private String phone;

    @Column("email")
    private String email;

    @Column("base_salary")
    private Double baseSalary;
}
