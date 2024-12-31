package io.aditya.kam.service.impl;

import io.aditya.kam.TestData;
import io.aditya.kam.entity.KeyAccountManager;
import io.aditya.kam.entity.KeyAccountManagerEntity;
import io.aditya.kam.repository.KeyAccountManagerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KeyAccountManagerServiceImplTest {

  @Mock
  private KeyAccountManagerRepository _keyAccountManagerRepository;

  @InjectMocks
  private KeyAccountManagerServiceImpl _keyAccountManagerServiceImpl;

  @Test
  public void testThatKeyAccountManagerIsSaved() {
    KeyAccountManager keyAccountManager = TestData.getKeyAccountManager();

    KeyAccountManagerEntity keyAccountManagerEntity = TestData.getKeyAccountManagerEntity();

    Mockito.when(_keyAccountManagerRepository.save(Mockito.eq(keyAccountManagerEntity)))
        .thenReturn(keyAccountManagerEntity);

    KeyAccountManager result = _keyAccountManagerServiceImpl.create(keyAccountManager);
    Assertions.assertEquals(result, keyAccountManager);
    Assertions.assertNotEquals(result, null);

  }

  @Test
  public void testThatFindByIdReturnsEmptyWhenNoKeyAccountManager() {

    Mockito.when(_keyAccountManagerRepository.findById(Mockito.eq("2")))
        .thenReturn(Optional.empty());
    Optional<KeyAccountManager> result = _keyAccountManagerServiceImpl.findById(2);
    Assertions.assertEquals(Optional.empty(), result);

  }

  @Test
  public void testThatFindByIdReturnsEmptyWhenNoKeyAc() {

    KeyAccountManager keyAccountManager = TestData.getKeyAccountManager();
    KeyAccountManagerEntity keyAccountManagerEntity = TestData.getKeyAccountManagerEntity();

    Mockito.when(_keyAccountManagerRepository.findById(Mockito.eq("1")))
        .thenReturn(Optional.of(keyAccountManagerEntity));

    Optional<KeyAccountManager> result = _keyAccountManagerServiceImpl.findById(1);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(keyAccountManager, result.get());

  }

  @Test
  public void testListKeyAccountManagersReturnsEmptyListWhenNoKeyAccountManagerExist() {
    Mockito.when(_keyAccountManagerRepository.findAll()).thenReturn(new ArrayList<KeyAccountManagerEntity>());
    final List<KeyAccountManager> result = _keyAccountManagerServiceImpl.listKeyAccountManagers();
    Assertions.assertEquals(0, result.size());
  }

  @Test
  public void testListKeyAccountManagersReturnsList() {
    final KeyAccountManagerEntity keyAccountManagerEntity = TestData.getKeyAccountManagerEntity();
    Mockito.when(_keyAccountManagerRepository.findAll()).thenReturn(List.of(keyAccountManagerEntity));
    final List<KeyAccountManager> result = _keyAccountManagerServiceImpl.listKeyAccountManagers();
    Assertions.assertEquals(1, result.size());
  }

  private int getStartMin(String s, int offset) {
    return ((Integer.parseInt(s.substring(0,2)) * 60 + Integer.parseInt(s.substring(3,5)) - offset/(60*1000)) + 24*60) % (60*24);
  }

  private int getEndMin(String s, int offset) {
    return ((Integer.parseInt(s.substring(8,10)) * 60 + Integer.parseInt(s.substring(11,12)) - offset/(60*1000)) + 24*60) % (60*24);
  }

  private void setStartHourAndMinute(char[] ch, int startHourInMinutes) {
    String s1 = String.valueOf(startHourInMinutes/60);
    if(s1.length()==1) {
      ch[0]='0';
      ch[1] = s1.charAt(0);
    } else {
      ch[0]=s1.charAt(0);
      ch[1] = s1.charAt(1);
    }

    String s2 = String.valueOf(startHourInMinutes%60);
    if(s2.length()==1) {
      ch[3]='0';
      ch[4] = s2.charAt(0);
    } else {
      ch[3]=s2.charAt(0);
      ch[4] = s2.charAt(1);
    }
    
  }

  private void setEndHourAndMinute(char[] ch, int endHourInMinutes) {
    String s1 = String.valueOf(endHourInMinutes/60);
    if(s1.length()==1) {
      ch[8]='0';
      ch[9] = s1.charAt(0);
    } else {
      ch[8]=s1.charAt(0);
      ch[9] = s1.charAt(1);
    }

    String s2 = String.valueOf(endHourInMinutes%60);
    if(s2.length()==1) {
      ch[11]='0';
      ch[12] = s2.charAt(0);
    } else {
      ch[11]=s2.charAt(0);
      ch[12] = s2.charAt(1);
    }

  }

  //TODO Remove this
  @Test
  public void lol(){
    //Put validations
    String t1 = "10:00 - 18:00 PST";
    String t2 = "01:00 - 09:00 PST";

    //tc 10:00 - 20:00 PST, 00:30 - 08:00, no coomon, use t2
    // t1 later 01:00 - 09:00 PST, 00:30 - 08:00, use t1
    // t1 end breach
    //Mention in comment (t2)customer timezone is always preferred

    int t1InUtcS = getStartMin(t1, TimeZone.getTimeZone(t1.substring(14)).getRawOffset());
    int t1InUtcE = getEndMin(t1, TimeZone.getTimeZone(t1.substring(14)).getRawOffset());
    int t2InUtcS = getStartMin(t2, TimeZone.getTimeZone(t1.substring(14)).getRawOffset());
    int t2InUtcE = getEndMin(t2, TimeZone.getTimeZone(t1.substring(14)).getRawOffset());
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
    if (t1InUtcS>=t2InUtcS && t1InUtcS+60<=t2InUtcE) {
      System.out.println("LOL");
      setStartHourAndMinute(ch, t1InUtcS);
      setEndHourAndMinute(ch, t1InUtcS+60);
    } else {
       //(t2InUtcS>=t1InUtcS && t2InUtcS+60<=t1InUtcE)
       setStartHourAndMinute(ch, t2InUtcS);
       setEndHourAndMinute(ch, t2InUtcS+60);
    }

    String b = new String(ch);
    System.out.println(b);
  }

}
