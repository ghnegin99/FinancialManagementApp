package repository;

import model.Category;
import model.Transaction;
import model.TransactionType;
import model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.YearMonth;


public class TransactionRepository {

    private static final String FILE_PATH = "data/transactions.txt";

    private UserRepository userRepository = new UserRepository();
    private CategoryRepository categoryRepository = new CategoryRepository();


    public List<Transaction> getAllTransactions() {

        List<Transaction> transactions = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return transactions;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }


                String[] data = line.split(",", -1);


                if (data.length < 7) {
                    System.out.println(
                            "Invalid transaction line: " + line
                    );
                    continue;
                }

                try {

                    int id = Integer.parseInt(data[0]);

                    int userId = Integer.parseInt(data[1]);
                    User user = userRepository.findById(userId);

                    if (user == null) {
                        System.out.println(
                                "User not found for transaction: " + line
                        );
                        continue;
                    }

                    String title = data[2];

                    double amount = Double.parseDouble(data[3]);

                    TransactionType type =
                            TransactionType.valueOf(data[4]);

                    int categoryId = Integer.parseInt(data[5]);
                    Category category =
                            categoryRepository.findById(categoryId);

                    if (category == null) {
                        System.out.println(
                                "Category not found for transaction: " + line
                        );
                        continue;
                    }

                    LocalDate date = LocalDate.parse(data[6]);


                    String description =
                            data.length > 7 ? data[7] : "";

                    Transaction transaction = new Transaction(
                            id,
                            user,
                            title,
                            amount,
                            type,
                            category,
                            date,
                            description
                    );

                    transactions.add(transaction);

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Invalid number in transaction: " + line
                    );

                } catch (IllegalArgumentException e) {

                    System.out.println(
                            "Invalid transaction data: " + line
                    );

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void addTransaction(Transaction transaction) {

        List<Transaction> transactions = getAllTransactions();

        transactions.add(transaction);

        saveAllTransactions(transactions);
    }


    public Transaction findById(int id) {

        List<Transaction> transactions =
                getAllTransactions();

        for (Transaction transaction : transactions) {

            if (transaction.getId() == id) {

                return transaction;
            }
        }

        return null;
    }

    public List<Transaction> getTransactionsByUser(User user) {

        List<Transaction> result = new ArrayList<>();

        for (Transaction transaction : getAllTransactions()) {

            if (transaction.getUser().getId() == user.getId()) {

                result.add(transaction);

            }

        }

        return result;
    }

    public void updateTransaction(Transaction updatedTransaction) {

        List<Transaction> transactions = getAllTransactions();

        for (int i = 0; i < transactions.size(); i++) {

            if (transactions.get(i).getId() == updatedTransaction.getId()) {

                transactions.set(i, updatedTransaction);

                break;

            }

        }

        saveAllTransactions(transactions);

    }

    public void deleteTransaction(int id) {

        List<Transaction> transactions = getAllTransactions();

        transactions.removeIf(transaction ->
                transaction.getId() == id);

        saveAllTransactions(transactions);

    }

    private void saveAllTransactions(List<Transaction> transactions) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Transaction transaction : transactions) {

                writer.write(
                        transaction.getId() + "," +
                                transaction.getUser().getId() + "," +
                                transaction.getTitle() + "," +
                                transaction.getAmount() + "," +
                                transaction.getType() + "," +
                                transaction.getCategory().getId() + "," +
                                transaction.getDate() + "," +
                                transaction.getDescription()
                );

                writer.newLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Transaction> getTransactionsByCategory(Category category) {

        List<Transaction> result = new ArrayList<>();

        for (Transaction transaction : getAllTransactions()) {

            if (transaction.getCategory().getId() == category.getId()) {

                result.add(transaction);

            }

        }

        return result;

    }

    public List<Transaction> getTransactionsByUserCategoryAndMonth(User user,
                                                                   Category category,
                                                                   YearMonth month) {

        List<Transaction> result = new ArrayList<>();

        for (Transaction transaction : getAllTransactions()) {

            if (transaction.getUser().getId() == user.getId()
                    && transaction.getCategory().getId() == category.getId()
                    && YearMonth.from(transaction.getDate()).equals(month)
                    && transaction.getType() == TransactionType.EXPENSE) {

                result.add(transaction);

            }

        }

        return result;

    }


    public int getNextId() {

        int max = 0;

        List<Transaction> transactions =
                getAllTransactions();

        for (Transaction transaction : transactions) {

            if (transaction.getId() > max) {

                max = transaction.getId();

            }

        }

        return max + 1;

    }
}