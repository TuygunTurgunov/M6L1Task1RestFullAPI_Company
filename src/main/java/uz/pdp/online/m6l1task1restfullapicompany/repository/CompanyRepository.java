package uz.pdp.online.m6l1task1restfullapicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {

    boolean existsByAddress_HomeNumberAndAddress_StreetAndIdNot(String address_homeNumber, String address_street, Integer id);

    boolean existsByCorpNameAndIdNot(String corpName, Integer id);



}
