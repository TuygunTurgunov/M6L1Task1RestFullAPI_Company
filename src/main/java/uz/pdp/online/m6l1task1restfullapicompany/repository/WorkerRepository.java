package uz.pdp.online.m6l1task1restfullapicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {


    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);






}