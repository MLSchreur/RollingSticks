package nl.rollingsticks.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Eigenaar;

@Component
public interface EigenaarRepository extends CrudRepository <Eigenaar, Long>{

}
