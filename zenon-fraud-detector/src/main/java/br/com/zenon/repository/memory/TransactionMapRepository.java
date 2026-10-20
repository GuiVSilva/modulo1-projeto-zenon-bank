package br.com.zenon.repository.memory;

import br.com.zenon.repository.TransactionRepository;
import br.com.zenon.model.Transaction;

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

    @Override
    public void save(Transaction transaction) {
        this.transactionByOrginName.putIfAbsent(transaction.origin().name(), transaction);
    }
}
