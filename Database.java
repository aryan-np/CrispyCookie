package CrispyCookie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Method to Insert a Competitor into the Database
    public static void saveCompetitor(Competitor competitor) {
        String sql = "INSERT INTO Competitors (CompetitorID, Name, Level, Age, Score1, Score2, Score3, Score4, Score5) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, competitor.getCompetitorId());
            pstmt.setString(2, competitor.competitorName);
            pstmt.setInt(3, competitor.competitionLevel);
            pstmt.setInt(4, competitor.getCompetitorAge());

            List<Integer> scores = competitor.getScore();
            pstmt.setInt(5, scores.get(0));
            pstmt.setInt(6, scores.get(1));
            pstmt.setInt(7, scores.get(2));
            pstmt.setInt(8, scores.get(3));
            pstmt.setInt(9, scores.get(4));

            pstmt.executeUpdate();
            System.out.println("Competitor added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to Retrieve All Competitors from the Database
    public static List<Competitor> getAllCompetitors() {
        List<Competitor> competitors = new ArrayList<>();
        String sql = "SELECT * FROM Competitors";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("CompetitorID");
                String name = rs.getString("Name");
                int level = rs.getInt("Level");
                int age = rs.getInt("Age");

                Competitor competitor = new Competitor(id, name, level, age);
                competitor.addScore(rs.getInt("Score1"));
                competitor.addScore(rs.getInt("Score2"));
                competitor.addScore(rs.getInt("Score3"));
                competitor.addScore(rs.getInt("Score4"));
                competitor.addScore(rs.getInt("Score5"));

                competitors.add(competitor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competitors;
    }

    // Method to Update a Competitor’s Name and Scores
    public static void updateCompetitor(int competitorId, String newName, List<Integer> newScores) {
        String sql = "UPDATE Competitors SET Name = ?, Score1 = ?, Score2 = ?, Score3 = ?, Score4 = ?, Score5 = ? WHERE CompetitorID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setInt(2, newScores.get(0));
            pstmt.setInt(3, newScores.get(1));
            pstmt.setInt(4, newScores.get(2));
            pstmt.setInt(5, newScores.get(3));
            pstmt.setInt(6, newScores.get(4));
            pstmt.setInt(7, competitorId);

            pstmt.executeUpdate();
            System.out.println("Competitor updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to Delete a Competitor
    public static void deleteCompetitor(int competitorId) {
        String sql = "DELETE FROM Competitors WHERE CompetitorID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, competitorId);
            pstmt.executeUpdate();
            System.out.println("Competitor deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Competitor> getCompetitorsByLevel(int level) {
        List<Competitor> competitors = new ArrayList<>();
        String sql = "SELECT * FROM Competitors WHERE Level = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, level);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("CompetitorID");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");

                Competitor competitor = new Competitor(id, name, level, age);
                competitor.addScore(rs.getInt("Score1"));
                competitor.addScore(rs.getInt("Score2"));
                competitor.addScore(rs.getInt("Score3"));
                competitor.addScore(rs.getInt("Score4"));
                competitor.addScore(rs.getInt("Score5"));

                competitors.add(competitor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competitors;
    }

    // Get the top scorer in a given level
    public static Competitor getTopScorerByLevel(int level) {
        List<Competitor> competitors = getCompetitorsByLevel(level);

        if (competitors.isEmpty()) {
            return null; // No competitors found
        }

        Competitor topScorer = competitors.get(0);
        int highestScore = topScorer.getTotalScore();

        for (Competitor competitor : competitors) {
            if (competitor.getTotalScore() > highestScore) {
                topScorer = competitor;
                highestScore = competitor.getTotalScore();
            }
        }
        return topScorer;
    }
}
