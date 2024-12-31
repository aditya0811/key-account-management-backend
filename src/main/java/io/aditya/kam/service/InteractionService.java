package io.aditya.kam.service;

import io.aditya.kam.entity.Interaction;
import java.util.List;
import java.util.Optional;


public interface InteractionService {

  Interaction create(Interaction interaction);
  Optional<Interaction> findById(String id);
  List<Interaction> listInteractions();
}
