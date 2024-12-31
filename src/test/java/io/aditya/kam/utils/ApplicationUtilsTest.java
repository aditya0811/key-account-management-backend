package io.aditya.kam.utils;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ApplicationUtilsTest {

  @Test
  public void testGetCommonTimeHours() {

    String actualMeetingTime = ApplicationUtils
        .getCommonTimeHours("10:00 - 18:00 PST", "01:00 - 09:00 PST");
    Assertions.assertEquals(actualMeetingTime, "09:00 - 10:00 UTC");

    actualMeetingTime = ApplicationUtils
        .getCommonTimeHours("10:00 - 18:00 PST", "05:00 - 13:00 PST");
    Assertions.assertEquals(actualMeetingTime, "18:00 - 19:00 UTC");
    //TODO remove
//    System.out.println(ApplicationUtils.getCurrentTimestamp());
//    System.out.println(ApplicationUtils.getDateWithFrequencyOfCalls(0, "09:00 - 10:00 IST"));
//    System.out.println(LocalDate.now());
  }
}
