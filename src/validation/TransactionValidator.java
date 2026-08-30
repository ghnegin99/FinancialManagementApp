package validation;

import model.Transaction;

public class TransactionValidator {

    public static String validate(Transaction transaction) {

        if (transaction.getTitle() == null ||
                transaction.getTitle().trim().isEmpty()) {

            return "Title cannot be empty.";

        }

        if (transaction.getAmount() <= 0) {

            return "Amount must be greater than zero.";

        }

        if (transaction.getCategory() == null) {

            return "Category is required.";

        }

        if (transaction.getDate() == null) {

            return "Date is required.";

        }

        if (transaction.getType() == null) {

            return "Transaction type is required.";

        }

        return null;

    }

}