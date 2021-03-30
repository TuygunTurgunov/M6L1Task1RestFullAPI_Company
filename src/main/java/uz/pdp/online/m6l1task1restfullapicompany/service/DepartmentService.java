package uz.pdp.online.m6l1task1restfullapicompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Company;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Department;
import uz.pdp.online.m6l1task1restfullapicompany.payload.DepartmentDto;
import uz.pdp.online.m6l1task1restfullapicompany.payload.Result;
import uz.pdp.online.m6l1task1restfullapicompany.repository.CompanyRepository;
import uz.pdp.online.m6l1task1restfullapicompany.repository.DepartmentRepository;

import java.util.Optional;

@Component
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    /**
     * Add Department
     * @param departmentDto DepartmentDto
     * @return ResponseEntity<Result>
     */
    public Result addDepartment(DepartmentDto departmentDto){
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new Result("Company not found by id",false);

        boolean exists = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (exists)
            return new Result("Such kind of department already exists in this company",false);


        Company company = optionalCompany.get();
        Department department=new Department();
        department.setCompany(company);
        department.setName(departmentDto.getName());

        departmentRepository.save(department);
        return new Result("Department saved",true);
    }


    /**
     * Get Department PAge
     * @param page Integer
     * @return ResponseEntity<Page<Department>>
     */
    public Page<Department> getDepartmentPage(Integer page){
        Pageable pageable= PageRequest.of(page,3);
        return departmentRepository.findAll(pageable);
    }


    /**
     * Get one Department
     * @param id Integer
     * @return ResponseEntity<Department>
     */
    public  Department getOneDepartment(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    public  Result editDepartment(Integer id,DepartmentDto departmentDto){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if(!optionalDepartment.isPresent()){
            return new Result("Department not found by id",false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new Result("Company not found by id",false);


        boolean exists = departmentRepository.existsByNameAndCompanyIdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(), id);
        if (exists)
            return new Result("Department already exists",false);

        Department department = optionalDepartment.get();
        department.setCompany(optionalCompany.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new Result("Department edited",true);

    }

    public  Result deleteDepartment(Integer id){
        try {
            departmentRepository.deleteById(id);
            return new Result("Department deleted",true);
        }catch (Exception e){
            return new Result("Error in deleting",false);
        }


    }



}