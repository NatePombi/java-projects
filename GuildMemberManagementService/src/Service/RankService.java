package Service;

import Model.Member;
import Model.RankedMember;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankService {

    public static List<RankedMember> getRankedMembers(List<Member> list){

        return  list.stream()
                .map(RankedMember::new)
                .sorted()
                .toList();
    }
}
