package tournament;

public record TournamentNode(TournamentNode left, TournamentNode right, String winner, int points) {}
