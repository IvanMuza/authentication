package co.com.crediya.api.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoanApplicationDto {
    private String firstName;
    private String lastName;
    private String email;
    private Double baseSalary;
}
