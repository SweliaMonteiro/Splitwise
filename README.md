# Splitwise Service

## Problem Statement
Implement a Splitwise Service using the Design Patterns and MVC model. Splitwise contains the following tables:

1. User (ID, Name, Password, PhoneNumber)
2. Group (ID, Name, Admin, Members, Expenses)
3. Expense (ID, Description, Amount, CreatedBy, Group, UserExpenses)
4. UserExpenses (ID, Expense, User, Amount)


## Requirements

1. Register new User
   - Register an User to Splitwise with the given userName and password.
   - Make sure there is only one registration per mobile number.

2. Create new Group and to add members to the Group
   - Create a new group using the Group name and Admin name provided in the input.
   - Add a new member to the given groupId. Throw exception if UserId or GroupId is invalid.
  
3. Add an Expense to the Group
   - Throw Exception if invalid GroupId passed.
   - Create a new Expense using the details provided input. For the given amount, create PaidBy and HadToPayBy UserExpense object.

4. Settle Up User and Group
- Generate a list of Transactions to settle up a given User if the User is valid.
- Generate a list of Transactions to settle up a given Group if the Group is valid.
