package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.CashFlowDTO;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.*;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cashflow")
@CrossOrigin(origins = "*")
public class CashFlowController {

    @Autowired
    CashFlowService cashFlowService;

    @Autowired
    HelperUtil helper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CashFlowDTO>> getCashFlowForLoggedInUser(
            @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateTo,
            Authentication authentication
    ) {
        UserEntity user = helper.getUser(authentication);
        return new ResponseEntity<>(cashFlowService.getCashFlowForUser(dateFrom, dateTo, user), HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity exportDataToExcel(@RequestBody CashFlowDTO[] data) {
        byte[] excelContent;
        HttpHeaders headers = new HttpHeaders();
        try {
            excelContent = cashFlowService.exportDataToExcel(Arrays.asList(data));
            if (excelContent.length != 0) {
                headers.setContentDispositionFormData("Attachment", "data.xls");
                headers.setContentLength(excelContent.length);

                return new ResponseEntity<>(excelContent, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("download failed", headers, HttpStatus.NO_CONTENT);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Error writing xls", HttpStatus.I_AM_A_TEAPOT);
        }
    }


}
