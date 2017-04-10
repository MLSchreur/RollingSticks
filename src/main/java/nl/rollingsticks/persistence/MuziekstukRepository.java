package nl.rollingsticks.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Muziekstuk;

@Component
public interface MuziekstukRepository extends CrudRepository <Muziekstuk, Long>{

}
