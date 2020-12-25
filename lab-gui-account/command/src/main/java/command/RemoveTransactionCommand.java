package command;

import model.Account;
import model.Transaction;

import java.util.List;

public class RemoveTransactionCommand implements Command{


    private List<Transaction> transactionsToDelete;
    private Account account;

    public RemoveTransactionCommand(List<Transaction> transactionToAdd, Account account) {
        this.transactionsToDelete = transactionToAdd;
        this.account = account;
    }


    @Override
    public void execute() {
        transactionsToDelete.forEach((transaction -> account.removeTransaction(transaction)));
    }

    @Override
    public String getName() {
        return transactionsToDelete.size() + " Removed transactions";
    }

    @Override
    public void undo() {
        transactionsToDelete.forEach((transaction -> account.addTransaction(transaction)));
    }

    @Override
    public void redo() {
        execute();
    }
}