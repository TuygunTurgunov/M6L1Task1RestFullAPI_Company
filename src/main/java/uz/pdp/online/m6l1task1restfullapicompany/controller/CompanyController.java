package uz.pdp.online.m6l1task1restfullapicompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Company;
import uz.pdp.online.m6l1task1restfullapicompany.payload.CompanyDto;
import uz.pdp.online.m6l1task1restfullapicompany.payload.Result;
import uz.pdp.online.m6l1task1restfullapicompany.service.CompanyService;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    /**
     * Add Company
     * @param companyDto CompanyDto
     * @return ResponseEntity<Result>
     */
    @PostMapping
    public ResponseEntity<Result> addCompany(@Valid @RequestBody CompanyDto companyDto){
        Result result = companyService.addCompany(companyDto);
        if (result.getSuccess()){
            return ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    /**
     * Get Company Pages
     * @param page Integer
     * @return Page<Company>
     */
    @GetMapping
    public ResponseEntity<Page<Company>> getCompanyPage(@RequestParam Integer page){
        Page<Company> companyPage = companyService.getCompanyPage(page);
        return ResponseEntity.ok(companyPage);
    }

    /**
     * Get one Company by id
     * @param id Integer
     * @return ResponseEntity<Company>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> getOneCompany(@PathVariable Integer id){

        Company oneCompany = companyService.getOneCompany(id);
        if (oneCompany!=null)
            return ResponseEntity.ok(oneCompany);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(oneCompany);
    }

    /**
     * Edit Company
     * @param id Integer
     * @param companyDto CompanyDto
     * @return ResponseEntity<Result>
     */
    @PutMapping("/{id}")
    public  ResponseEntity<Result> editCompany(@PathVariable Integer id,@Valid @RequestBody CompanyDto companyDto){
        Result result = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(result.getSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(result);
    }

    /**
     * Delete Company
     * @param id Integer
     * @return ResponseEntity<Result>
     */
    @DeleteMapping("/{id}")
    public  ResponseEntity<Result> deleteCompany(@PathVariable Integer id){
        Result result = companyService.deleteCompany(id);
        return ResponseEntity.status(result.getSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(result);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }












}
