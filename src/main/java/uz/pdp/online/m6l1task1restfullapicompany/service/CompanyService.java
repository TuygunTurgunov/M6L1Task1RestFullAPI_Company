package uz.pdp.online.m6l1task1restfullapicompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Address;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Company;
import uz.pdp.online.m6l1task1restfullapicompany.payload.CompanyDto;
import uz.pdp.online.m6l1task1restfullapicompany.payload.Result;
import uz.pdp.online.m6l1task1restfullapicompany.repository.AddressRepository;
import uz.pdp.online.m6l1task1restfullapicompany.repository.CompanyRepository;

import java.util.Optional;

@Component
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;


    /**
     * Add Company
     * @param companyDto CompanyDto
     * @return ResponseEntity<Result>
     */
    public Result addCompany(CompanyDto companyDto){
        boolean existsAddress = addressRepository.existsByHomeNumberAndStreet(companyDto.getHomeNumber(), companyDto.getStreet());
        if (existsAddress)
            return new Result("address already exists",false);
        Address address=new Address();
        address.setHomeNumber(companyDto.getHomeNumber());
        address.setStreet(companyDto.getStreet());
        Address saveAddress = addressRepository.save(address);
        Company company=new Company();
        company.setAddress(saveAddress);
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new Result("Company saved",true);
    }

    /**
     * Get Company Pages
     * @param page Integer
     * @return Page<Company>
     */
    public Page<Company> getCompanyPage(Integer page){
        Pageable pageable= PageRequest.of(page,3);
        Page<Company> companyPage = companyRepository.findAll(pageable);
        return companyPage;
    }

    /**
     * Get one Company by id
     * @param id Integer
     * @return ResponseEntity<Company>
     */
    public Company getOneCompany(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }


    /**
     * Edit Company
     * @param id Integer
     * @param companyDto CompanyDto
     * @return ResponseEntity<Result>
     */
    public Result editCompany(Integer id,CompanyDto companyDto){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new Result("Company not found by id",false);


        //check by address
        boolean checkAddress = companyRepository.existsByAddress_HomeNumberAndAddress_StreetAndIdNot(companyDto.getHomeNumber(), companyDto.getStreet(), id);
        if (checkAddress)
            return new Result("This address already exists ",false);
        //check by company name
        boolean checkName = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (checkName)
            return new Result("such kind of company name already exists",false);


        Company company = optionalCompany.get();
        Address address = company.getAddress();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address editedAddress = addressRepository.save(address);
        company.setAddress(editedAddress);
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new Result("Company edited", true);

    }


    /**
     * Delete Company
     * @param id Integer
     * @return ResponseEntity<Result>
     */
    public Result deleteCompany(Integer id){
        try {
            companyRepository.deleteById(id);
            return new Result("Company deleted",true);
        }catch (Exception e){
            return new Result("Error in deleting",false);
        }

    }














}
