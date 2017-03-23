package nl.rollingsticks.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.rollingsticks.domain.Huiswerkopdracht;


@Component
public interface HuiswerkopdrachtRepository extends CrudRepository <Huiswerkopdracht, Long>{

}
