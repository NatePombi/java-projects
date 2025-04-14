package Service;

import Model.Member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Guild {

    private List<Member> members;

    public Guild(List<Member> members) {
        this.members = (members != null) ? members : new ArrayList<>();
    }

    /** Adds a member from the guild
     *
     * @param member the member thats getting added
     * @return true if the member is added and false if they not.
     */
    public boolean addMember(Member member){
        if(member== null || findMember(member.getName())){
            return false;
        }

        members.add(member);
        return true;
    }

    /** Removes a member from the Guild
     *
     * @param name is the name of the member that needs to get removed
     * @return true if the member is successfully removed and false if not
     */
    public boolean removeMember(String name){

       if(name == null || name.isBlank()){return false;}

       Iterator<Member> m = members.iterator();

       while(m.hasNext()){
           if(m.next().getName().equalsIgnoreCase(name)){
               m.remove();
               return true;
           }
       }

       return false;
    }


    /** Finding member in the guild
     *
     * @param name is the name of the member being looked for
     * @return true if member is found and false if not
     */
    private  boolean findMember(String name){

        for(Member m : members){
            if(m.getName().equalsIgnoreCase(name)){
                return true;
            }
        }

        return false;
    }



    public List<Member> getAllMembers(){
        return new ArrayList<>(members);
    }
}
