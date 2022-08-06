import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public final class SummarizeClients {
    public static void main(String[] args) {

        int numClients ;
        int totalQuarterlySpend;
        double averageBudget;
        long nextExpiration;
        // If we use Set we won't need the distinct() method
        // Set<ZoneId> representedZoneIds = new HashSet<>();
        // We will use the distinct() method for learning purpose so we will use a list
        List<ZoneId> representedZoneIds = new ArrayList<>();
        // We use Long instead of Integer
        Map<Year, Long> contractsPerYear = new HashMap<>();

        // Get a list of all clients
        List<UdacisearchClient> clients = ClientStore.getClients();

        // 1- Sum of all clients quarterly budgets
        totalQuarterlySpend = clients.stream()
                .mapToInt(UdacisearchClient::getQuarterlyBudget)
                .sum();

        // 2- Get total number of clients
        numClients = clients.size();

        // 3- Average Budget
        averageBudget = clients.stream()
                .mapToDouble(UdacisearchClient::getQuarterlyBudget).average()
                .orElse(0.0);

        //  4- The id of the client whose contract is next to expire
        nextExpiration = clients.stream()
                .min(Comparator.comparing(UdacisearchClient::getContractEnd))
                .map(UdacisearchClient::getId)
                .orElse(-1L);
        // 5- Represented Zones ID
        representedZoneIds = clients.stream()
                .flatMap(client -> client.getTimeZones().stream())
                .distinct().collect(Collectors.toList());

        // 6- A map whose keys are Year and whose values are the number of contracts starting that year
        contractsPerYear  = clients.stream()
                                    .collect(Collectors
                                            .groupingBy(
                                                    SummarizeClients::getContractYear,
                                                    Collectors.counting()));


        System.out.println("Num clients: " + numClients);
        System.out.println("Total quarterly spend: " + totalQuarterlySpend);
        System.out.println("Average budget: " + averageBudget);
        System.out.println("ID of next expiring contract: " + nextExpiration);
        System.out.println("Client time zones: " + representedZoneIds);
        System.out.println("Contracts per year: " + contractsPerYear);
    }

    private static Year getContractYear(UdacisearchClient client) {
        LocalDate contractDate =
                LocalDate.ofInstant(client.getContractStart(), client.getTimeZones().get(0));
        return Year.of(contractDate.getYear());
    }
}
