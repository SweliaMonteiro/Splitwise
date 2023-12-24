package com.test.splitwise.controllers;

import com.test.splitwise.dto.*;
import com.test.splitwise.enums.ResponseType;
import com.test.splitwise.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SettleUpController {

    @Autowired
    private SettleUpService settleUpService;

    public SettleUpUserResponseDTO settleUpUser(SettleUpUserRequestDTO request) {
        SettleUpUserResponseDTO response = new SettleUpUserResponseDTO();

        try {
            List<TransactionDTO> transactions = settleUpService.settleUpUser(request.getUserName());
            response.setTransactions(transactions);
            response.setResponseType(ResponseType.SUCCESS);
        }
        catch (Exception e) {
            response.setFailureMessage(e.getMessage());
            response.setResponseType(ResponseType.FAILURE);
        }

        return response;
    }


    public SettleUpGroupResponseDTO settleUpGroup(SettleUpGroupRequestDTO request) {
        SettleUpGroupResponseDTO response = new SettleUpGroupResponseDTO();

        try {
            List<TransactionDTO> transactions = settleUpService.settleUpGroup(request.getGroupName());
            response.setTransactions(transactions);
            response.setResponseType(ResponseType.SUCCESS);
        }
        catch (Exception e) {
            response.setFailureMessage(e.getMessage());
            response.setResponseType(ResponseType.FAILURE);
        }

        return response;
    }

}
