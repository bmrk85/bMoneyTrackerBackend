package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.CashFlowDTO;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface CashFlowService {

    byte[] exportDataToExcel(List<CashFlowDTO> cashFlowDTOList) throws IOException;

    List<CashFlowDTO> getCashFlowForUser(Date dateFrom, Date dateTo, UserEntity user);

}