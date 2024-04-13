package com.example.commands;

import com.example.controllers.SettleUpController;
import com.example.dto.*;
import com.example.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SettleUpGroupCommand implements Command {

    @Autowired
    private SettleUpController settleUpController;


    @Override
    public boolean matches(String input) {
        // SettleUpGroup <groupName>
        String[] inputArray = input.trim().split(" ");
        if(inputArray.length==2 && inputArray[0].equalsIgnoreCase("SettleUpGroup")) {
            return true;
        }
        return false;
    }


    @Override
    public void execute(String input) {
        String[] inputArray = input.trim().split(" ");
        SettleUpGroupRequestDTO request = new SettleUpGroupRequestDTO();
        request.setGroupName(inputArray[1]);

        SettleUpGroupResponseDTO response = settleUpController.settleUpGroup(request);

        if(response.getResponseType().equals(ResponseType.SUCCESS)) {
            for (TransactionDTO transaction : response.getTransactions()) {
                System.out.print("Payer:" + transaction.getSupposeToPay().getName());
                System.out.print(",  Receiver:" + transaction.getSupposeToReceive().getName());
                System.out.println(",  Amount:" + transaction.getAmount());
            }
        }
        else {
            System.out.println("Error to settle the Group : " + response.getFailureMessage());
        }
    }

}
