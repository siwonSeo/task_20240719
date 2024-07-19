package com.company.employee.enums;

import com.company.common.code.ErrorStatusMessage;
import com.company.common.exception.ApiException;
import com.company.employee.dto.request.RequestEmployee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum FileType {
    CSV(file -> {
        List<RequestEmployee> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                RequestEmployee data = new RequestEmployee(
                     values[0]
                    ,values[1]
                    ,values[2]
                    ,values[3]
                );
                dataList.add(data);
            }
        } catch (Exception e) {
            throw new ApiException(ErrorStatusMessage.INPUT_FILE_ERROR, "Csv 파일 처리 중 오류 발생");
        }
        return dataList;
    }),

    JSON(file -> {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(file.getInputStream(), RequestEmployee[].class));
        } catch (Exception e) {
            throw new ApiException(ErrorStatusMessage.INPUT_FILE_ERROR, "Json 파일 처리 중 오류 발생");
        }
    });

    public static List<RequestEmployee> processCsvString(String csvData) {
        List<RequestEmployee> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new StringReader(csvData))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                RequestEmployee data = new RequestEmployee(
                        values[0],
                        values[1],
                        values[2],
                        values[3]
                );
                dataList.add(data);
            }
        } catch (Exception e) {
            throw new ApiException(ErrorStatusMessage.INPUT_FILE_ERROR, "CSV 데이터 처리 중 오류 발생");
        }
        return dataList;
    }

    private final Function<MultipartFile, List<RequestEmployee>> processor;

    FileType(Function<MultipartFile, List<RequestEmployee>> processor) {
        this.processor = processor;
    }

    public List<RequestEmployee> process(MultipartFile file) {
        return processor.apply(file);
    }

    public static FileType detectFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if ("json".equals(extension)) {
                return JSON;
            } else if ("csv".equals(extension)) {
                return CSV;
            }
        }
        throw new ApiException(ErrorStatusMessage.INPUT_FILE_ERROR, "Json 파일 처리 중 오류 발생");
    }
}