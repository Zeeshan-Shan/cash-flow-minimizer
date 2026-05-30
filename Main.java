import java.util.*;

class Transaction {
    String from;
    String to;
    long amount;

    Transaction(String from, String to, long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "[" + from + ", " + to + ", " + amount + "]";
    }
}

public class Main {

    public static List<Transaction> minimizeCashFlow(List<List<Object>> transactions) {

        
        Map<String, Long> balanceMap = new HashMap<>();

        for (List<Object> t : transactions) {
            String debtor = (String) t.get(0);
            String creditor = (String) t.get(1);
            long amount = ((Number) t.get(2)).longValue();

            balanceMap.put(debtor,
                    balanceMap.getOrDefault(debtor, 0L) - amount);

            balanceMap.put(creditor,
                    balanceMap.getOrDefault(creditor, 0L) + amount);
        }

    
        PriorityQueue<Map.Entry<String, Long>> creditors =
                new PriorityQueue<>((a, b) ->
                        Long.compare(b.getValue(), a.getValue()));

        
        PriorityQueue<Map.Entry<String, Long>> debtors =
                new PriorityQueue<>((a, b) ->
                        Long.compare(Math.abs(b.getValue()),
                                Math.abs(a.getValue())));

        for (Map.Entry<String, Long> entry : balanceMap.entrySet()) {

            long balance = entry.getValue();

            if (balance > 0) {
                creditors.offer(
                        new AbstractMap.SimpleEntry<>(
                                entry.getKey(),
                                balance
                        )
                );
            } else if (balance < 0) {
                debtors.offer(
                        new AbstractMap.SimpleEntry<>(
                                entry.getKey(),
                                balance
                        )
                );
            }
        }

        List<Transaction> result = new ArrayList<>();

        while (!creditors.isEmpty() && !debtors.isEmpty()) {

            Map.Entry<String, Long> creditor = creditors.poll();
            Map.Entry<String, Long> debtor = debtors.poll();

            String debtorName = debtor.getKey();
            String creditorName = creditor.getKey();

            long debtorAmount = -debtor.getValue();
            long creditorAmount = creditor.getValue();

            long settledAmount = Math.min(debtorAmount, creditorAmount);

            result.add(
                    new Transaction(
                            debtorName,
                            creditorName,
                            settledAmount
                    )
            );

            long remainingDebtor = debtorAmount - settledAmount;
            long remainingCreditor = creditorAmount - settledAmount;

            if (remainingDebtor > 0) {
                debtors.offer(
                        new AbstractMap.SimpleEntry<>(
                                debtorName,
                                -remainingDebtor
                        )
                );
            }

            if (remainingCreditor > 0) {
                creditors.offer(
                        new AbstractMap.SimpleEntry<>(
                                creditorName,
                                remainingCreditor
                        )
                );
            }
        }

        return result;
    }

    public static void main(String[] args) {

        List<List<Object>> transactions = new ArrayList<>();

        transactions.add(Arrays.asList("Tom", "Jerry", 1000));
        transactions.add(Arrays.asList("Jerry", "Spike", 1000));
        transactions.add(Arrays.asList("Spike", "Tom", 500));

        List<Transaction> optimized =
                minimizeCashFlow(transactions);

        System.out.println("Optimized Transactions:");

        for (Transaction t : optimized) {
            System.out.println(t);
        }
    }
}