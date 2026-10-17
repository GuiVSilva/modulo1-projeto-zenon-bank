package br.com.zenon;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionMapRepository implements TransactionRepository {

    private final Map<String, Transaction> transactionByOrginName;

    public TransactionMapRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactionByOrginName =
                transactions
                        .stream()
                        .collect(Collectors.toMap(transaction -> transaction.origin().name(), transaction -> transaction));
    }

    @Override
    public Optional<Transaction> findByOriginName(String originName) {
        return Optional.ofNullable(transactionByOrginName.get(originName));
    }
}
