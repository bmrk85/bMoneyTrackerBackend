package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.CashFlowDTO;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CashFlowService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.IncomeService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SpendingService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class CashFlowServiceImpl implements CashFlowService {

    @Autowired
    HelperUtil helper;

    @Autowired
    IncomeService incomeService;

    @Autowired
    SpendingService spendingService;


    @Override
    public byte[] exportDataToExcel(List<CashFlowDTO> cashFlowDTOList) throws IOException {

        XSSFWorkbook workBook = new XSSFWorkbook();
        IndexedColorMap colorMap = workBook.getStylesSource().getIndexedColors();
        XSSFSheet sheet = workBook.createSheet("CashFlow");
        XSSFCellStyle headerStyle = workBook.createCellStyle();
        XSSFCellStyle rowStyle = workBook.createCellStyle();
        XSSFCellStyle incomeRowStyle = workBook.createCellStyle();
        XSSFCellStyle spendingRowStyle = workBook.createCellStyle();
        XSSFCellStyle dateRowStyle = workBook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(new XSSFColor(Color.LIGHT_GRAY, colorMap));
        headerStyle.setBorderBottom(BorderStyle.THICK);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFCreationHelper creationHelper = workBook.getCreationHelper();

        dateRowStyle.setDataFormat(creationHelper.createDataFormat().getFormat("MM dd, yyyy HH:mm:ss"));
        incomeRowStyle.setFillForegroundColor(new XSSFColor(new Color(152,251,152), colorMap));
        spendingRowStyle.setFillForegroundColor(new XSSFColor(new Color(255,160,122), colorMap));
        incomeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        spendingRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        incomeRowStyle.setDataFormat((short)7);
        spendingRowStyle.setDataFormat((short)7);

        rowStyle.setAlignment(HorizontalAlignment.LEFT);
        dateRowStyle.setAlignment(HorizontalAlignment.LEFT);
        incomeRowStyle.setAlignment(HorizontalAlignment.LEFT);
        spendingRowStyle.setAlignment(HorizontalAlignment.LEFT);


        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Category");
        header.createCell(2).setCellValue("Date");
        header.createCell(3).setCellValue("Amount");
        header.setRowStyle(headerStyle);
        int currentRow = 1;
        int totalAmount = 0;
        for (CashFlowDTO c : cashFlowDTOList) {
            XSSFRow row = sheet.createRow(currentRow++);
            row.createCell(0).setCellValue(c.getName());
            row.createCell(1).setCellValue(c.getCategory().getTitle());
            row.createCell(2).setCellStyle(dateRowStyle);
            row.getCell(2).setCellValue(c.getDate());

            row.createCell(3).setCellValue(c.getAmount());
            totalAmount+=c.getAmount();
            if (c.getAmount() < 0) {
                row.getCell(3).setCellStyle(spendingRowStyle);
            } else {
                row.getCell(3).setCellStyle(incomeRowStyle);
            }
            row.setRowStyle(rowStyle);
        }
        XSSFRow totalRow = sheet.createRow(currentRow);
        XSSFCellStyle totalRowStyle = workBook.createCellStyle();
        XSSFCellStyle totalCellStyle = workBook.createCellStyle();
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setBold(true);


        totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalRowStyle.setBorderTop(BorderStyle.THICK);
        totalCellStyle.setBorderTop(BorderStyle.THICK);
        totalRowStyle.setFont(font);
        totalCellStyle.setFont(font);

        totalRow.createCell(2).setCellValue("Total:");
        totalRow.createCell(3).setCellValue(totalAmount);

        if(totalAmount<0){
            totalCellStyle.setFillForegroundColor(new XSSFColor(new Color(255,160,122), colorMap));
        }else{
            totalCellStyle.setFillForegroundColor(new XSSFColor(new Color(152,251,152), colorMap));
        }
        totalRow.getCell(3).setCellStyle(totalCellStyle);

        totalCellStyle.setDataFormat((short)7);
        totalRow.setRowStyle(totalRowStyle);
        sheet.setDefaultColumnWidth(30);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workBook.write(stream);
        stream.close();

        return stream.toByteArray();
    }


    public List<CashFlowDTO> getCashFlowForUser(Date dateFrom, Date dateTo, UserEntity user){

        List<CashFlowDTO> cashFlowDTOS = helper
                .mapAll(incomeService.findAllByDateBetweenAndUserEntity_Id(dateFrom,dateTo, user.getId()),CashFlowDTO.class);
        cashFlowDTOS.addAll(helper.mapAll(spendingService.findAllByDateBetweenAndUserEntity_Id(dateFrom,dateTo,user.getId()),CashFlowDTO.class));

        return cashFlowDTOS;
    }


}
