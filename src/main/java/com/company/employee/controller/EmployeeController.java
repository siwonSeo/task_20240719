package com.company.employee.controller;

import com.company.employee.dto.request.RequestEmployee;
import com.company.employee.dto.response.ResponseEmployee;
import com.company.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/employee")
@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping(value = "")
    public ResponseEntity<List<ResponseEmployee>> empolyees() {
        return ResponseEntity.ok(employeeService.employees());
    }


    @GetMapping(value = "/{name}")
    public ResponseEntity<ResponseEmployee> empolyee(@PathVariable("name") String name) {
        return ResponseEntity.ok(employeeService.employee(name));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> file(@RequestParam("file") MultipartFile file) {
        employeeService.registEmployeesByFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> json(@RequestBody List<RequestEmployee> employees) {
        employeeService.registEmployeesByJson(employees);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> csv(@RequestBody String csvData) {
        employeeService.registEmployeesByCsv(csvData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
