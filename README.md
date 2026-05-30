# Cash Flow Minimizer

## Overview

Cash Flow Minimizer is a Java-based application that simplifies a complex network of debts among multiple people. Instead of executing every recorded transaction individually, the program calculates the net balance of each person and generates an optimized set of transactions to settle all debts with fewer transfers.

---

## Problem Statement

Given a list of transactions in the form:

Person A owes Person B amount X

The goal is to:

* Settle all balances completely.
* Preserve the total amount paid and received.
* Reduce unnecessary intermediate transactions.
* Generate an optimized list of payments.

---

## Approach

The solution uses a **Greedy Algorithm** with **Priority Queues (Max Heaps)**.

### Step 1: Calculate Net Balance

For every transaction:

* Debtor balance decreases.
* Creditor balance increases.

Example:

Transaction:

Tom owes Jerry ₹1000

Balance Update:

Tom = -1000

Jerry = +1000

A HashMap is used to maintain the balance of each person.

---

### Step 2: Separate Debtors and Creditors

After calculating net balances:

* Negative balance → Debtor (needs to pay)
* Positive balance → Creditor (needs to receive)
* Zero balance → Already settled

Two Priority Queues are created:

1. Creditors Queue

   * Stores people with positive balances.
   * Highest balance has highest priority.

2. Debtors Queue

   * Stores people with negative balances.
   * Largest debt has highest priority.

---

### Step 3: Greedy Settlement

While both queues are not empty:

1. Pick the largest debtor.
2. Pick the largest creditor.
3. Settle the minimum possible amount.
4. Record the transaction.
5. If any balance remains, insert it back into its respective queue.

This process continues until all balances become zero.

---

## Algorithm Flow

1. Read all transactions.
2. Compute net balance using HashMap.
3. Insert positive balances into Creditors Priority Queue.
4. Insert negative balances into Debtors Priority Queue.
5. Repeatedly match the highest debtor with the highest creditor.
6. Create optimized transactions.
7. Continue until all balances are settled.
8. Return the optimized transaction list.

---

## Data Structures Used

* HashMap<String, Long>

  * Stores net balance of each person.

* PriorityQueue<Map.Entry<String, Long>>

  * Maintains creditors and debtors based on balance priority.

* ArrayList<Transaction>

  * Stores optimized transactions.

---

## Time Complexity

Let N be the number of people with non-zero balance.

* Balance Calculation: O(T)
* Priority Queue Operations: O(N log N)

Overall Complexity:

O(T + N log N)

where T is the number of input transactions.

---

## Sample Input

```java
transactions.add(Arrays.asList("Tom", "Jerry", 1000));
transactions.add(Arrays.asList("Jerry", "Spike", 1000));
transactions.add(Arrays.asList("Spike", "Tom", 500));
```

## Sample Output

```text
[Tom, Spike, 500]
```

---

## Technologies Used

* Java
* HashMap
* Priority Queue (Max Heap)
* Greedy Algorithm

