package service;

import model.Transaction;
import model.TransactionType;
import model.User;
import repository.TransactionRepository;

import java.util.List;

public class TransactionService {

    private TransactionRepository repository =
            new TransactionRepository();

    public void addTransaction(Transaction transaction){

        transaction.setId(repository.getNextId());

        repository.addTransaction(transaction);

    }

    public void updateTransaction(Transaction transaction){

        repository.updateTransaction(transaction);

    }

    public void deleteTransaction(int id){

        repository.deleteTransaction(id);

    }

    public List<Transaction> getTransactions(User user){

        return repository.getTransactionsByUser(user);

    }

    public double getTotalIncome(User user){

        double total = 0;

        for(Transaction transaction :
                repository.getTransactionsByUser(user)){

            if(transaction.getType()==
                    TransactionType.INCOME){

                total += transaction.getAmount();

            }

        }

        return total;

    }

    public double getTotalExpense(User user){

        double total = 0;

        for(Transaction transaction :
                repository.getTransactionsByUser(user)){

            if(transaction.getType()==
                    TransactionType.EXPENSE){

                total += transaction.getAmount();

            }

        }

        return total;

    }
    public Transaction getTransactionById(int id){

        return repository.findById(id);

    }

    public double getBalance(User user){

        return getTotalIncome(user)
                - getTotalExpense(user);

    }

}