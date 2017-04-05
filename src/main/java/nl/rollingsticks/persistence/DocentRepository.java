package nl.rollingsticks.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Docent;

@Component
public interface DocentRepository extends CrudRepository<Docent, Long> {

}
