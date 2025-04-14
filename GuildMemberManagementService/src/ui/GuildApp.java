package ui;

import Model.Member;
import Model.RankedMember;
import Service.Guild;
import Service.RankService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuildApp {
    public static void main(String[] args) {
        //Creates a list of Members
        List<Member> members = new ArrayList<>(List.of(
                new Member("Heber", "Mage",LocalDate.of(2021,6,3),16),
                new Member("Nathan","Wizard King", LocalDate.of(2022,7,7),29),
                new Member("Kevin", "Mage", LocalDate.of(2023,7,21),20),
                new Member("derrick", "Civilian",LocalDate.of(2019,11,2),9)
        ));

        //Adds the list of members to the guild
        Guild guild = new Guild(members);


        //Prints out all the guild members (unRanked)
        System.out.println("Guild Members");
        for(var g : guild.getAllMembers()){
            System.out.println(g);
        }

        //Prints out Members ranked by accordingly
       System.out.println("\n\nRanked Member List:");
       for(RankedMember r : RankService.getRankedMembers(members)){
           System.out.println(r);
       }
    }
}
// Developed by Nathan
// A clean console app to manage and rank guild members
