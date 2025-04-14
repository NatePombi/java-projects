package Model;

import java.time.LocalDate;

public class Member implements Comparable<Member>{
    private String name;
    private String guildRole;
    private int missionsCompleted;
    private LocalDate joinDate;

    public Member(String name, String guildRole, LocalDate joinDate, int missionsCompleted) {
        this.name = name;
        this.guildRole = guildRole;
        this.joinDate = joinDate;
        this.missionsCompleted = missionsCompleted;
    }

    public String getName() {
        return name;
    }

    public String getGuildRole() {
        return guildRole;
    }

    public int getMissionsCompleted() {
        return missionsCompleted;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    @Override
    public String toString() {
        return "Name: %-10s  |  Role: %-10s  |  Missions Completed: %-10d  |  Year joined: %-10s".formatted(name,guildRole,missionsCompleted,joinDate.toString());
    }

    @Override
    public int compareTo(Member o) {
        return Integer.compare(this.missionsCompleted, o.missionsCompleted);
    }
}
