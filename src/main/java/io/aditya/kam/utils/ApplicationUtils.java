package io.aditya.kam.utils;

import io.aditya.kam.dto.Customer;
import io.aditya.kam.dto.CustomerOrder;
import io.aditya.kam.dto.Interaction;
import io.aditya.kam.enums.LeadStatus;
import io.aditya.kam.service.CustomerOrderService;
import io.aditya.kam.service.CustomerService;
import io.aditya.kam.service.KeyAccountManagerService;
import io.aditya.kam.service.PointOfContactService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TimeZone;


public class ApplicationUtils {

  /**
   * Find common 1 working hour between the point of contact and key account manager,
   * and if there is no common working hour then we return working hours as
   * point of contact prefers
   * @param kamWorkingHours HH:mm - HH:mm XYZ
   * @param pocWorkingHours HH:mm - HH:mm XYZ
   * @return HH:mm - HH:mm UTC
   */
  public static String getCommonTimeHours(String kamWorkingHours,
      String pocWorkingHours) {

    int kamStartingMin = getTimeInMinutes(kamWorkingHours, TimeZone.getTimeZone(kamWorkingHours.substring(14)).getRawOffset(),0);
    int kamEndingMin = getTimeInMinutes(kamWorkingHours, TimeZone.getTimeZone(kamWorkingHours.substring(14)).getRawOffset(), 8);
    int pocStartingMin = getTimeInMinutes(pocWorkingHours, TimeZone.getTimeZone(pocWorkingHours.substring(14)).getRawOffset(), 0);
    int pocEndingMin = getTimeInMinutes(pocWorkingHours, TimeZone.getTimeZone(pocWorkingHours.substring(14)).getRawOffset(), 8);
    char[] ch = new char[17];
    ch[2]=':';
    ch[5]=' ';
    ch[6]='-';
    ch[7]=' ';
    ch[10]=':';
    ch[13]=' ';
    ch[14]='U';
    ch[15]='T';
    ch[16]='C';
    if (kamStartingMin >= pocStartingMin && kamStartingMin+60 <= pocEndingMin) {
      setHourAndMinute(ch, kamStartingMin, 0);
      setHourAndMinute(ch, kamStartingMin+60, 8);
    } else {
      //(t2InUtcS>=t1InUtcS && t2InUtcS+60<=t1InUtcE)
      setHourAndMinute(ch, pocStartingMin, 0);
      setHourAndMinute(ch, pocStartingMin+60, 8);
    }
    return new String(ch);
  }

  /**
   * For start we are setting up 0:2 for hours and 3:5 for minutes (ind=0)
   * For end we are setting up 8:10 for hours and 11:13 for minutes (ind=8)
   * @param ch
   * @param minutesOffset
   * @param ind
   */
  private static void setHourAndMinute(char[] ch, int minutesOffset, int ind) {
    String hour = String.valueOf(minutesOffset/60);
    if(hour.length()==1) {
      ch[ind]='0';
      ch[ind+1] = hour.charAt(0);
    } else {
      ch[ind]=hour.charAt(0);
      ch[ind+1] = hour.charAt(1);
    }

    String minutes = String.valueOf(minutesOffset%60);
    if(minutes.length()==1) {
      ch[ind+3]='0';
      ch[ind+4] = minutes.charAt(0);
    } else {
      ch[ind+3] = minutes.charAt(0);
      ch[ind+4] = minutes.charAt(1);
    }

  }

  /**
   * Here we are trying to convert HH:mm into number of minutes offset from UTC 00:00
   * For start we are picking up 0:2 for hours and 3:5 for minutes (ind=0)
   * For end we are picking up 8:10 for hours and 11:13 for minutes (ind=8)
   * @param timeInHoursAndMinute
   * @param offset
   * @param ind
   * @return
   */
  private static int getTimeInMinutes(String timeInHoursAndMinute, int offset, int ind) {
    return ((Integer.parseInt(timeInHoursAndMinute.substring(ind,ind+2)) * 60
        + Integer.parseInt(timeInHoursAndMinute.substring(ind+3,ind+5))
        - offset/(60*1000)) + 24*60) % (60*24);
  }

  /**
   * Here we are adding frequencyOfCallInDays to current date considering pointOfContact timezone
   * @param frequencyOfCallInDays frequencyOfCallInDays
   * @param pointOfContactWorkingHours pointOfContactWorkingHours
   * @return date when frequencyOfCallInDays is added to currentDate
   */
  public static String getDateWithFrequencyOfCalls(Integer frequencyOfCallInDays, String pointOfContactWorkingHours) {
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String pointOFContactTimezone = pointOfContactWorkingHours.substring(14);
//    Date date = new Date();


    return LocalDate.now(TimeZone.getTimeZone(pointOFContactTimezone).toZoneId())
        .plusDays(frequencyOfCallInDays)
        .toString();
  }

  /**
   *
   * Here we are trying to return current date + X frequency days, along with common time hour.
   * @param keyAccountManagerService keyAccountManagerService
   * @param pointOfContactService pointOfContactService
   * @param customer customer
   * @return YYYY-MM-DD HH:mm UTC
   */
  public static String getDateWithFrequencyOfCalls(KeyAccountManagerService keyAccountManagerService,
      PointOfContactService pointOfContactService, Customer customer) {
    String keyAccountManagerWorkingHours = keyAccountManagerService.findById(customer.getKeyAccountManagerID()).get()
        .getWorkingHours();
    String pointOfContactWorkingHours = pointOfContactService.findById(customer.getPointOfContactID()).get()
        .getWorkingHours();
    return ApplicationUtils.getDateWithFrequencyOfCalls(customer.getFrequencyOfCallsInDays(), pointOfContactWorkingHours)
        + " " +
        ApplicationUtils.getCommonTimeHours(keyAccountManagerWorkingHours, pointOfContactWorkingHours);

  }

  /**
   * Returns current date timestamp in certain format
   * @return yyyy-MM-dd HH:mm
   */
  public static String getCurrentTimestamp() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return now.format(formatter);
  }

  /**
   * Returns current date in certain format
   * @return yyyy-MM-dd
   */
  public static String getCurrentDate() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return now.format(formatter);
  }


}
