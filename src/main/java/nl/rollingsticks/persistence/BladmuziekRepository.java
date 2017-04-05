package nl.rollingsticks.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Bladmuziek;

@Component
public interface BladmuziekRepository extends CrudRepository <Bladmuziek, Long>{

}
