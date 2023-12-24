package com.test.splitwise.commands;

import com.test.splitwise.controllers.SettleUpController;
import com.test.splitwise.dto.SettleUpUserRequestDTO;
import com.test.splitwise.dto.SettleUpUserResponseDTO;
import com.test.splitwise.dto.TransactionDTO;
import com.test.splitwise.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpUserCommand implements Command {

    @Autowired
    private SettleUpController settleUpController;

    @Override
    public boolean matches(String input) {
        // SettleUpUser <userName>
        String[] inputArray = input.trim().split(" ");
        if(inputArray.length==2 && inputArray[0].equalsIgnoreCase("SettleUpUser")) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        String[] inputArray = input.trim().split(" ");
        SettleUpUserRequestDTO request = new SettleUpUserRequestDTO();
        request.setUserName(inputArray[1]);

        SettleUpUserResponseDTO response = settleUpController.settleUpUser(request);

        if(response.getResponseType().equals(ResponseType.SUCCESS)) {
            for (TransactionDTO transaction : response.getTransactions()) {
                System.out.print("Payer:" + transaction.getSupposeToPay().getName());
                System.out.print(",  Receiver:" + transaction.getSupposeToReceive().getName());
                System.out.println(",  Amount:" + transaction.getAmount());
            }
        }
        else {
            System.out.println("Error to settle the User : " + response.getFailureMessage());
        }
    }

}
