package io.aditya.kam.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.aditya.kam.TestData;
import io.aditya.kam.dto.KeyAccountManager;
import io.aditya.kam.service.KeyAccountManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser("admin")
public class KeyManagerControllerIntegrationTest {

  @Autowired
  private MockMvc _mockMvc;

  @Autowired
  private KeyAccountManagerService _keyAccountManagerService;


  private static ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testThatKeyAccountManagerIsCreated()
      throws Exception {
    final KeyAccountManager keyAccountManager = TestData.getKeyAccountManager();
    _mockMvc.perform(MockMvcRequestBuilders.post("/v1/key-account-managers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(keyAccountManager)))

        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.keyAccountManagerID").value(1));

  }

  @Test
  public void testThatKeyAccountManagerExists()
      throws Exception {
    final KeyAccountManager keyAccountManager = TestData.getKeyAccountManager();
    _keyAccountManagerService.create(keyAccountManager);
    _mockMvc.perform(MockMvcRequestBuilders.get("/v1/key-account-managers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(keyAccountManager)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.keyAccountManagerID").value(1));

  }

  @Test
  public void testThatKeyAccountManagerNotExists() throws Exception {
    _mockMvc.perform(MockMvcRequestBuilders.get("/v1/key-account-managers/" + 22))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testThatListKeyAccountManagersReturnsHttp200EmptyListWhenNoKeyAccountManagersExist() throws Exception {
    _mockMvc
        .perform(MockMvcRequestBuilders.get("/v1/key-account-managers"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  public void testThatListKeyAccountManagersReturnsHttp200AndWhenKeyAccountManagersExist() throws Exception {
    final KeyAccountManager keyAccountManager = TestData.getKeyAccountManager();
    _keyAccountManagerService.create(keyAccountManager);

    _mockMvc
        .perform(MockMvcRequestBuilders.get("/v1/key-account-managers"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].keyAccountManagerID")
            .value(1));
  }

}
