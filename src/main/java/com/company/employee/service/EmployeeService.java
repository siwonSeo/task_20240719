package com.company.employee.service;

import com.company.common.code.ErrorStatusMessage;
import com.company.common.exception.ApiException;
import com.company.employee.dto.request.RequestEmployee;
import com.company.employee.dto.response.ResponseEmployee;
import com.company.employee.entity.Employee;
import com.company.employee.enums.FileType;
import com.company.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<ResponseEmployee> employees(){
        return employeeRepository.findAll().stream().map(er->new ResponseEmployee(
             er.getName()
            ,er.getEmail()
            ,er.getTell()
            ,er.getJoined()
        )).toList();
    }

    public ResponseEmployee employee(String name){
        Employee employee = employeeRepository.findById(name).orElseThrow(() -> new ApiException(ErrorStatusMessage.USER_NOT_FOUND));
        return new ResponseEmployee(
             employee.getName()
            ,employee.getEmail()
            ,employee.getTell()
            ,employee.getJoined()
        );
    }

    @Transactional
    public void registEmployeesByFile(MultipartFile file) {
        log.info("MultipartFile:{}",file);
        List<RequestEmployee> requestEmployees = FileType.detectFileType(file).process(file);
        this.saveAllEmployee(requestEmployees);
    }

    @Transactional
    public void registEmployeesByJson(List<RequestEmployee> requestEmployees) {
        log.info("requestEmployees:{}",requestEmployees);
        saveAllEmployee(requestEmployees);
    }

    @Transactional
    public void registEmployeesByCsv(String csvData) {
        log.info("csvData:{}",csvData);
        List<RequestEmployee> paresdRequestEmployees = FileType.processCsvString(csvData);
        saveAllEmployee(paresdRequestEmployees);
    }

    private void saveAllEmployee(List<RequestEmployee> requestEmployees){
        List<Employee> employees = requestEmployees.stream()
                .map(re -> new Employee(
                                re.name()
                                ,re.email()
                                ,re.tell().replaceAll("-","")
                                ,LocalDate.parse(re.joined().replaceAll("-","").replaceAll("\\.",""), DateTimeFormatter.BASIC_ISO_DATE)
                        )
                ).toList();
        log.info("employees:{}",employees);
        employeeRepository.saveAll(employees);
    }

    public static void main(String[] args){
        String a = "2024-01-01";
        String b = "2024.01.01";
        System.out.println(LocalDate.parse(a.replaceAll("-",""), DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(LocalDate.parse(a.replaceAll("-",""), DateTimeFormatter.ofPattern("yyyyMMdd")));
        System.out.println(LocalDate.parse(b.replaceAll("\\.",""), DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(LocalDate.parse(b.replaceAll("\\.",""), DateTimeFormatter.ofPattern("yyyyMMdd")));


    }
}

