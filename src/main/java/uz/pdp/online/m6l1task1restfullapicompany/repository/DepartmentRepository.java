package uz.pdp.online.m6l1task1restfullapicompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    boolean existsByNameAndCompanyId(String name, Integer company_id);

    boolean existsByNameAndCompanyIdAndIdNot(String name, Integer company_id, Integer id);



}
