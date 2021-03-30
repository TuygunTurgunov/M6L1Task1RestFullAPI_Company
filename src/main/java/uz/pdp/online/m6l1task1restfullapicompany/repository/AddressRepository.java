package uz.pdp.online.m6l1task1restfullapicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {

    boolean existsByHomeNumberAndStreet(String homeNumber, String street);

}
