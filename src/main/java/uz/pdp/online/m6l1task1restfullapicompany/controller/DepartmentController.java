package uz.pdp.online.m6l1task1restfullapicompany.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Department;
import uz.pdp.online.m6l1task1restfullapicompany.payload.DepartmentDto;
import uz.pdp.online.m6l1task1restfullapicompany.payload.Result;
import uz.pdp.online.m6l1task1restfullapicompany.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    /**
     * Add Department
     * @param departmentDto DepartmentDto
     * @return ResponseEntity<Result>
     */
    @PostMapping
    public ResponseEntity<Result> addDepartment(@Valid @RequestBody DepartmentDto departmentDto){

        Result result = departmentService.addDepartment(departmentDto);
        if (result.getSuccess())
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    /**
     * Get Department PAge
     * @param page Integer
     * @return ResponseEntity<Page<Department>>
     */
    @GetMapping
    public ResponseEntity<Page<Department>> getDepartmentPage(@RequestParam Integer page){

        Page<Department> departmentPage = departmentService.getDepartmentPage(page);
        return ResponseEntity.status(!departmentPage.isEmpty()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(departmentPage);

    }

    /**
     * Get one Department
     * @param id Integer
     * @return ResponseEntity<Department>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getOneDepartment(@PathVariable Integer id){
        Department oneDepartment = departmentService.getOneDepartment(id);
        return ResponseEntity.status(oneDepartment!=null?HttpStatus.OK:HttpStatus.NOT_FOUND).body(oneDepartment);
    }

    /**
     * Edit Department
     * @param id Integer
     * @param departmentDto DepartmentDto
     * @return ResponseEntity<Result>
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result>editDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto){

        Result result = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(result.getSuccess()?202:409).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteDepartment(@PathVariable Integer id){
        Result result = departmentService.deleteDepartment(id);
        return ResponseEntity.status(result.getSuccess()?202:409).body(result);
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