package uz.pdp.online.m6l1task1restfullapicompany.payload;

import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {

    @NotNull(message = "Department name not be empty")
    private String name;

    @NotNull(message = "Company id not be empty")
    private Integer companyId;

}
