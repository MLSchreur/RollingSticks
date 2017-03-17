package nl.rollingsticks.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import nl.rollingsticks.rest.service.BladmuziekEndpoint;
import nl.rollingsticks.rest.service.EigenaarEndpoint;
import nl.rollingsticks.rest.service.LeerlingEndpoint;
import nl.rollingsticks.rest.service.TekstEndpoint;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(){
		register(TekstEndpoint.class);
		register(BladmuziekEndpoint.class);
		register(EigenaarEndpoint.class);
		register(LeerlingEndpoint.class);
	}
}
