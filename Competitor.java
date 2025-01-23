package CrispyCookie;

public class Competitor {
    private final int competitorId;
    public String competitorName;
    public final String competitionLevel;
    private int competitorAge;

    public Competitor(int competitorId,String competitorName,String competitionLevel,int competitorAge){
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

    public double getOverallScore() { return 5; }

    public String getFullDetails(){
        return "Competitor "+competitorName+
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
}
