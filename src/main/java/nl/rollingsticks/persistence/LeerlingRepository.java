package nl.rollingsticks.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Leerling;

@Component
public interface LeerlingRepository extends CrudRepository <Leerling, Long>{

}
