package nl.rollingsticks.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TekstRepository extends CrudRepository <Tekst, Long>{

}
