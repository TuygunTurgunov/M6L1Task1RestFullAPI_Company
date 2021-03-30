package uz.pdp.online.m6l1task1restfullapicompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Address;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Department;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Worker;
import uz.pdp.online.m6l1task1restfullapicompany.payload.Result;
import uz.pdp.online.m6l1task1restfullapicompany.payload.WorkerDto;
import uz.pdp.online.m6l1task1restfullapicompany.repository.AddressRepository;
import uz.pdp.online.m6l1task1restfullapicompany.repository.DepartmentRepository;
import uz.pdp.online.m6l1task1restfullapicompany.repository.WorkerRepository;

import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;

    public Result addWorker(WorkerDto workerDto){
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("This worker already exists",false);
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new Result("Department not found by id",false);

        Address address=new Address();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        Address savedAddress = addressRepository.save(address);

        Worker worker=new Worker();
        worker.setAddress(savedAddress);
        worker.setDepartment(optionalDepartment.get());
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        workerRepository.save(worker);
        return new Result("Worker saved",true);
    }
    public Page<Worker> getWorkerPage(Integer page){

        Pageable pageable= PageRequest.of(page,3);
        return workerRepository.findAll(pageable);
    }

    public Worker getOneWorker(Integer id){

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }
    public Result editWorker(Integer id,WorkerDto workerDto){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new Result("Worker not found by id",false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new Result("Department not found",false);

        boolean andIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (andIdNot)
            return new Result("This phone number already exists",false);



        Worker worker = optionalWorker.get();
        Address address = worker.getAddress();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        addressRepository.save(address);
        worker.setAddress(address);
        worker.setName(workerDto.getName());
        worker.setDepartment(optionalDepartment.get());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        workerRepository.save(worker);
        return new Result("Worker edited",true);

    }
    public Result deleteWorker(Integer id){

       try {
           workerRepository.deleteById(id);
           return new Result("deleted",true);
       }catch (Exception e){

           return new Result("error delete",false);

       }


    }







}