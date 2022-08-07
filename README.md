# SummarizeClients - Stream API


There is a simple Java program, `SummarizeClients`, 
that computes some basic summary information using all the information from the clients.
`SummarizeClients` is refactored. It used to use `for` loops cross all the client data to compute the summary,
now it uses the Java Stream API.

The new code contains a single Stream-based computation for each of the following pieces of summary metrics:

- `totalQuarterlySpend` - The sum of all clients' quarterly budgets.
- `averageBudget` - The arithmetic mean of all clients' quarterly budgets.
   The `average()` method is helpful.
- `nextExpiration` - The `id` of the client whose contract is next to expire. 
   Since this variable has type `long`, 
   I am assigning a default value to this using the `orElse()` method, 
   I made sure my default value used a `long` literal such as `-1L` instead of just `-1`.
- `representedZoneIds` - The collection of all time zones where any clients are located. 
   `Stream#flatMap())` is helpful.
- `contractsPerYear` - A map whose keys are a `java.time. Year`,
   and whose values are the number of contracts starting in that year. 
   `Collectors.groupingBy(...)` will be helpful.

## Running the code using the command line

To compile and run this code, just do:
   
      javac -cp . SummarizeClients.java 
      java -cp . SummarizeClients 

The output should look like:

    Num clients: 4
    Total quarterly spend: 27500
    Average budget: 6875.0
    ID of next expiring contract: 1
    Client time zones: [America/Los_Angeles, America/Denver, America/Tijuana, Asia/Manila, Asia/Bangkok, Australia/NSW, Australia/Melbourne]
    Contracts per year: {2017=2, 2019=2}
