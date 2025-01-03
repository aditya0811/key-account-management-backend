package io.aditya.kam.service;

import io.aditya.kam.dto.Interaction;
import java.util.List;
import java.util.Optional;


public interface InteractionService {

  Interaction create(Interaction interaction);
  Optional<Interaction> findById(Integer id);
  List<Interaction> listInteractions();
}
