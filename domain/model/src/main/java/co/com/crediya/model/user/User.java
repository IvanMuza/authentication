package co.com.crediya.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String documentNumber;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String address;

    private String phone;

    private String email;

    private Double baseSalary;
}
