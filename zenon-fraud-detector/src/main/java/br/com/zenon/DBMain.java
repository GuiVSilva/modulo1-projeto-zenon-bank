package br.com.zenon;

import java.math.BigDecimal;
import java.util.List;

public class DBMain {
    void main() {
        ConnectionFactory.getConnection();
        IO.println("Conexao com DB criada com sucesso!");

        var repository = new TransactionSQLRepository();

        long startTimeSQL = System.nanoTime();
        var transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        IO.println("Quantidade de Transações: " + transactions.size());
        IO.println("Iniciando adição das transações no BD...");

        transactions.forEach(repository::save);

        long endTimeSQL = System.nanoTime();
        IO.println("Tempo de inserção - SQL (ms): " + (endTimeSQL - startTimeSQL) / 1_000_000.0);

        repository.findByOriginName("C1231006815")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao não encontrada para C1000001"));

        repository.findByOriginName("C12345")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao não encontrada para C12345"));
    }
}
