package com.test.splitwise.strategies;

import com.test.splitwise.dto.TransactionDTO;
import com.test.splitwise.dto.UserDTO;
import com.test.splitwise.enums.UserExpenseType;
import com.test.splitwise.models.Expense;
import com.test.splitwise.models.User;
import com.test.splitwise.models.UserExpense;
import com.test.splitwise.repositories.UserExpenseRepository;
import com.test.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HeapSettleUpStrategy implements SettleUpStrategy {

    @Autowired
    private UserExpenseRepository userExpenseRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<TransactionDTO> settleUp(List<Expense> expenses) {
        // Calculate total Expense per User
        Map<Integer, Integer> expensePerUser = new HashMap<>();

        for(Expense expense : expenses) {
            // Get the list of UserExpense for each expense
            List<UserExpense> userExpenses = userExpenseRepository.findAllByExpense(expense);

            // Iterate through the userExpenses
            for(UserExpense userExpense : userExpenses) {

                // Add the amount of userExpense if UserExpenseType is PAID
                if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)) {
                    expensePerUser.put(userExpense.getUser().getId(),
                            expensePerUser.getOrDefault(userExpense.getUser().getId(), 0) + userExpense.getAmount());
                }
                // Subtract the amount of userExpense if UserExpenseType is HAD_TO_PAY
                else {
                    expensePerUser.put(userExpense.getUser().getId(),
                            expensePerUser.getOrDefault(userExpense.getUser().getId(), 0) - userExpense.getAmount());
                }
            }
        }

        // Segregate the total Expense per User into payers and receivers into Max Priority Queue
        // Payers have negative amount and Receivers have positive amount
        PriorityQueue<Pair> payers = new PriorityQueue<>((x, y) -> y.getAmount() - x.getAmount());
        PriorityQueue<Pair> receivers = new PriorityQueue<>((x, y) -> y.getAmount() - x.getAmount());
        for(Integer userId : expensePerUser.keySet()) {
            if(expensePerUser.get(userId) < 0) {
                payers.add(new Pair(userId, Math.abs(expensePerUser.get(userId))));
            }
            else {
                receivers.add(new Pair(userId, expensePerUser.get(userId)));
            }
        }

        // Generate the transactions
        List<TransactionDTO> transactions = new ArrayList<>();

        // Iterate until both the queues are not empty
        while(!payers.isEmpty() && !receivers.isEmpty()) {
            // Get the user who will need to pay the most
            Pair payer = payers.remove();
            // Get the user who will get the most
            Pair receiver = receivers.remove();
            // Payer will always pay the receiver, get the minimum amount paid
            int paidAmount = Math.min(receiver.getAmount(), payer.getAmount());

            // If amount to be paid by payer is more than the amount to be received by the receiver
            // Then the receiver is settled, so pay the full amount to the receiver
            // Update the payer amount and add back to the payers queue
            if(receiver.getAmount() < payer.getAmount()) {
                payers.add(new Pair(payer.getUserId(), paidAmount));
            }
            // If amount to be paid by payer is less than the amount to be received by the receiver
            // Then the payer is settled, so pay the partial amount to the receiver
            // Update the receiver amount and add back to the receivers queue
            else if(receiver.getAmount() > payer.getAmount()) {
                receivers.add(new Pair(receiver.getUserId(), paidAmount));
            }

            // Create a new transaction
            TransactionDTO transaction = new TransactionDTO();
            // Set SupposeToPay User for the transaction
            Optional<User> optionalPayerUser = userRepository.findById(payer.getUserId());
            transaction.setSupposeToPay(new UserDTO(optionalPayerUser.get().getId(), optionalPayerUser.get().getName(), optionalPayerUser.get().getPhoneNumber()));
            // Set SupposeToReceive User for the transaction
            Optional<User> optionalReceiverUser = userRepository.findById(receiver.getUserId());
            transaction.setSupposeToReceive(new UserDTO(optionalReceiverUser.get().getId(), optionalReceiverUser.get().getName(), optionalReceiverUser.get().getPhoneNumber()));
            // Set amount of the transaction
            transaction.setAmount(paidAmount);

            transactions.add(transaction);
        }

        return transactions;
    }

}
