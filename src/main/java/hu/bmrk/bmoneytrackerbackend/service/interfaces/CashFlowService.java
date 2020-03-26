package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.CashFlowDTO;

import java.io.IOException;
import java.util.List;

public interface CashFlowService {

    byte[] exportDataToExcel(List<CashFlowDTO> cashFlowDTOList) throws IOException;

}
