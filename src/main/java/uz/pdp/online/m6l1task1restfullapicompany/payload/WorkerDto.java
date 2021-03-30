package uz.pdp.online.m6l1task1restfullapicompany.payload;

import lombok.Data;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Address;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {

    @NotNull(message = "name not be empty")
    private String name;

    @NotNull(message = "phone number not be empty")
    private String phoneNumber;

    @NotNull(message = "street not be empty")
    private String street;

    @NotNull(message = "home number not be empty")
    private String homeNumber;


    @NotNull(message = "department not be empty")
    private Integer departmentId;

}