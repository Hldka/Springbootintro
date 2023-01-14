package com.tpe.dto;

import com.tpe.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentDTO {
    private Long id;

    @NotNull(message = "first name can not be null")
    @NotBlank(message="fist name can not be white space")
    @Size(min=2,max=25,message =
            "First name '${validatedValue}' must be between {min} and {max} long")

    private String firstName; // A

    private String lastName;

    private  Integer grade;



    @Email(message="Provide valid email")
    private String email; //xxx@yyy.com

    private String phoneNumber;


    private LocalDateTime createDate = LocalDateTime.now();

    public StudentDTO(Student student){//studentDTO  std1=new StudentDTO(student)
        this.id= student.getId();
        this.firstName = student.getName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.grade = student.getGrade();
        this.phoneNumber= student.getPhoneNumber();
        this.createDate = student.getCreateDate();

    }

}
