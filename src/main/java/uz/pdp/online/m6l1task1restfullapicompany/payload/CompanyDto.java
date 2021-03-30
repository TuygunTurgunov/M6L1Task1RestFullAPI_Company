package uz.pdp.online.m6l1task1restfullapicompany.payload;

import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {

    @NotNull(message = "company name not be empty")
    private String corpName;
    @NotNull(message = "director name not be empty")
    private String directorName;
    @NotNull(message = "street not be empty")
    private String street;
    @NotNull(message = "home number not be empty")
    private String homeNumber;




}
