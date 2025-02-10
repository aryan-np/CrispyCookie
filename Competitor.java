package CrispyCookie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Competitor {
    private final int competitorId;
    public String competitorName;
    public final int competitionLevel;
    private int competitorAge;

    List<Integer> scores=new ArrayList<>(5);
    public Competitor(int competitorId,String competitorName,int competitionLevel,int competitorAge){
        this.competitorId=competitorId;
        this.competitorName=competitorName;
        this.competitionLevel=competitionLevel;
        this.competitorAge=competitorAge;
    }

    public int getCompetitorId() {
        return competitorId;
    }

    public int getCompetitorAge() {
        return competitorAge;
    }

    public List<Double> getOverallScore() {
        List<Double> retVals = new ArrayList<>(3);
        int total=getTotalScore();
        double weightedAverageScore = total/scores.size();
        retVals.add(weightedAverageScore);
        Collections.sort(scores);
        double avg=0;
        for (int i = 0; i <scores.size()-1 ; i++) {
            avg+= scores.get(i);
        }
        Competitor topScorer = Database.getTopScorerByLevel(competitionLevel);
        double topScore = topScorer.getTotalScore()/5;
        retVals.add(avg);
        retVals.add(topScore);
        return retVals;
    }

    public String getFullDetails(){
        return "Competitor "+competitorName+
                "\n Age :"+competitorAge+
                "\n Level "+competitionLevel+
                "\n Score"+ getOverallScore();
    }

    public String getShortDetail(){
        String initials="MEh";
        try{
            String[] name = competitorName.split(" ");
            initials=""+name[0].charAt(0)+name[1].charAt(0);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return String.format("CN + %d (%s) has a final score of %f",competitorId,initials,getOverallScore());

    }

    public List<Integer> getScore() {
        return scores;
    }

    public void addScore(int score) {
        if (scores.size() < 5) {
            scores.add(score);
        } else {
            System.out.println("Cannot add more than 5 scores.");
        }
    }
    public int getTotalScore() {
        int total = 0;
        for (int score : scores) {
            total += score;
        }
        return total;
    }
}
