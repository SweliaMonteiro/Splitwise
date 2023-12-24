package com.test.splitwise.commands;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandExecutor {

    private Map<Command, String> commands;

    public CommandExecutor(RegisterUserCommand registerUserCommand, SettleUpUserCommand settleUpUserCommand, SettleUpGroupCommand settleUpGroupCommand, CreateGroupCommand createGroupCommand, AddGroupMemberCommand addGroupMemberCommand, AddGroupExpenseCommand addGroupExpenseCommand) {
        commands = new HashMap<>();
        commands.put(registerUserCommand, "RegisterUser <userName> <phoneNumber> <password>");
        commands.put(settleUpUserCommand, "SettleUpUser <userId>");
        commands.put(settleUpGroupCommand, "SettleUpGroup <groupName>");
        commands.put(createGroupCommand, "CreateGroup <groupName> <adminName>");
        commands.put(addGroupMemberCommand, "AddGroupMember <groupName> <userName>");
        commands.put(addGroupExpenseCommand, "AddGroupExpense <groupName> <createdByUserName> <description> <amount> <paidBy> <supposeToPayBy>");
    }

    public void add(Command command, String helperString) {
        commands.put(command, helperString);
    }

    public void remove(Command command) {
        commands.remove(command);
    }

    public boolean printHelperStrings(String input) {
        if(input.trim().equalsIgnoreCase("Help")) {
            System.out.println("Please find below the different commands available: ");
            for(Command command : commands.keySet()) {
                System.out.println("-- " + commands.get(command));
            }
            return true;
        }
        return false;
    }

    public void execute(String input) {
        if(printHelperStrings(input)) return;

        for(Command command : commands.keySet()) {
            if(command.matches(input)) {
                command.execute(input);
                return;
            }
        }

        throw new RuntimeException("Command not found!");
    }

}
