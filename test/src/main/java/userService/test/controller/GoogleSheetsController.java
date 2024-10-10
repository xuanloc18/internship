package userService.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import userService.test.dto.response.APIResponse;
import userService.test.service.GoogleSheetsService;

import java.io.IOException;

@RestController
@RequestMapping("/api/sheets")
public class GoogleSheetsController {

    @Autowired
    private GoogleSheetsService googleSheetsService;

    @PostMapping("/import")
    public APIResponse<String> importData(@RequestParam String spreadsheetId, @RequestParam String range) throws IOException {
        googleSheetsService.importDataFromGoogleSheets(spreadsheetId, range);
        return  APIResponse.<String>builder()
                .result("import successfull")
                .build();
    }

    @PostMapping("/export")
    public APIResponse<String> exportData(@RequestParam String spreadsheetId, @RequestParam String range) throws IOException {
        googleSheetsService.exportDataToGoogleSheets(spreadsheetId, range);
        return  APIResponse.<String>builder()
                .result("Export successfull")
                .build();
    }
//    POST http://localhost:8080/service/api/sheets/import?spreadsheetId=1E4SJKpcjhDBtmnpYYHWAy_YjjtXzfL36T1oxfcc889o&range=A1:E
//    POST http://localhost:8080/service/api/sheets/export?spreadsheetId=1E4SJKpcjhDBtmnpYYHWAy_YjjtXzfL36T1oxfcc889o&range=A1:E

}