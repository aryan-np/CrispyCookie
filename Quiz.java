package CrispyCookie;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static List<Question> retrieveQuestions(int level) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT question, option1, option2, option3, option4, answer FROM quiz WHERE level = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, level);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question q = new Question(
                        rs.getString("question"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getInt("answer")
                );
                questions.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }
    }
}

