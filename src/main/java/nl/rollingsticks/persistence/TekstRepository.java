package nl.rollingsticks.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Tekst;

@Component
public interface TekstRepository extends CrudRepository <Tekst, Long>{

}
