package Utility;

import java.time.LocalDate;
import java.time.Period;

public class ServiceDurationCalculator {
    /*
    Calculates the full years of members service

      @param joinDate the date the member joined
      @return number of years the member served
     */

    public static int getYearsOfService(LocalDate joinDate) {
        if(isValid(joinDate)){
            return Period.between(joinDate, LocalDate.now()).getYears();
        }
        return 0;
    }

    /*
    Calculates the full months of member service

    @param joinDate the date the member joined
    @return number of months the member has served
     */
    public static int getMonthsOfService(LocalDate joinDate) {
        if(!isValid(joinDate)){return 0;}
        return Period.between(joinDate, LocalDate.now()).getMonths();
    }

    /*
    Calculates the full Lenght of service(e.g 2 years 2 months etc)

    @param joinDate the date the member joined
    @return the full length of member service period
     */
    public static Period getFullServicePeriod(LocalDate joinDate) {
        if(!isValid(joinDate)){return Period.ZERO;}
        return Period.between(joinDate, LocalDate.now());
    }


    /*
    Checks if the date provided is not null or if its not future Date

    @param joinDate  the date the member joined
    @return True if the date is valid
     */
    private static boolean isValid(LocalDate joinDate){
        return joinDate != null && !joinDate.isAfter(LocalDate.now());
    }
}
