package Model;

import Utility.ServiceDurationCalculator;

import java.util.Map;

enum ranks{
      ROOKIE(1), VETERAN(2), ELITE(3);

    private final int weight;

    ranks(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
public class RankedMember implements Comparable<RankedMember>{

    private Member member;
    private ranks rank;
    private int yearsofService;
    private  int totalMissions;

    public RankedMember(Member members) {
        this.member = members;
        totalMissions = members.getMissionsCompleted();
        yearsofService = ServiceDurationCalculator.getYearsOfService(members.getJoinDate());
        rank = totalMissions >=20 ? ranks.ELITE : totalMissions>= 10 ? ranks.VETERAN : ranks.ROOKIE;
    }

    public ranks getRank() {
        return rank;
    }

    public int getYearsofService() {
        return yearsofService;
    }

    public int getTotalMissions() {
        return totalMissions;
    }

    /**
     *
     * @return the enum as a Pascal case String
     */

    private String formatRank(){
        String name = rank.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return "Name: %s  |  Role: %s  |  Rank: %s  |  Missions Completed: %d  |  Years of Service: %d \n".formatted(member.getName(), member.getGuildRole(), formatRank(), member.getMissionsCompleted(), yearsofService);
    }

    /**
     *
     * @param o the object to be compared.
     * @return
     */

    @Override
    public int compareTo(RankedMember o) {

        int result = Integer.compare(o.rank.getWeight(), this.rank.getWeight());

        if(result == 0){
            return Integer.compare(o.totalMissions , this.totalMissions);
        }
        return result;
    }
}
