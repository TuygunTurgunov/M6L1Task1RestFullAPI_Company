package uz.pdp.online.m6l1task1restfullapicompany.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.m6l1task1restfullapicompany.entity.Worker;
import uz.pdp.online.m6l1task1restfullapicompany.payload.Result;
import uz.pdp.online.m6l1task1restfullapicompany.payload.WorkerDto;
import uz.pdp.online.m6l1task1restfullapicompany.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;
    @PostMapping
    public ResponseEntity<Result> addWorker(@Valid @RequestBody  WorkerDto workerDto){
        Result result = workerService.addWorker(workerDto);
        return ResponseEntity.status(result.getSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }

    @GetMapping
    public ResponseEntity<Page<Worker>> getWorkerPage(@RequestParam Integer page){
        Page<Worker> workerPage = workerService.getWorkerPage(page);
        return ResponseEntity.ok(workerPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneWorker(@PathVariable Integer id){
        Worker oneWorker = workerService.getOneWorker(id);
        return ResponseEntity.status(oneWorker!=null?200:404).body(oneWorker);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editWorker(@PathVariable Integer id,@Valid @RequestBody WorkerDto workerDto){
        Result result = workerService.editWorker(id, workerDto);
        return ResponseEntity.status(result.getSuccess()?202:409).body(result);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable Integer id){
        Result result = workerService.deleteWorker(id);
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
