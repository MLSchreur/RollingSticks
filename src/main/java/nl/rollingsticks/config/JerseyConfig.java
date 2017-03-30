package nl.rollingsticks.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import nl.rollingsticks.rest.service.BladmuziekEndpoint;
import nl.rollingsticks.rest.service.LeerlingEndpoint;

import nl.rollingsticks.rest.service.MuziekstukEndpoint;
import nl.rollingsticks.rest.service.StorageEndpoint;
import nl.rollingsticks.rest.service.GroepEndpoint;
import nl.rollingsticks.rest.service.HuiswerkopdrachtEndpoint;

import nl.rollingsticks.rest.service.DocentEndpoint;

import nl.rollingsticks.rest.service.TekstEndpoint;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(){
		register(TekstEndpoint.class);
		register(BladmuziekEndpoint.class);
		register(MuziekstukEndpoint.class);
		register(GroepEndpoint.class);
		register(HuiswerkopdrachtEndpoint.class);
		register(LeerlingEndpoint.class);
		register(DocentEndpoint.class);
		register(StorageEndpoint.class);
	}
}
