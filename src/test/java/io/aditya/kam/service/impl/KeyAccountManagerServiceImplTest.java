package io.aditya.kam.service.impl;

import io.aditya.kam.TestData;
import io.aditya.kam.entity.KeyAccountManager;
import io.aditya.kam.entity.KeyAccountManagerEntity;
import io.aditya.kam.repository.KeyAccountManagerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

}
