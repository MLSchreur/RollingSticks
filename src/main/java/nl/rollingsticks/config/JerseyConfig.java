package nl.rollingsticks.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import nl.rollingsticks.rest.service.EigenaarEndpoint;
import nl.rollingsticks.rest.service.LeerlingEndpoint;
import nl.rollingsticks.rest.service.MuziekstukEndpoint;
import nl.rollingsticks.rest.service.GroepEndpoint;
import nl.rollingsticks.rest.service.HuiswerkopdrachtEndpoint;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(){
		register(MuziekstukEndpoint.class);
		register(GroepEndpoint.class);
		register(HuiswerkopdrachtEndpoint.class);
		register(EigenaarEndpoint.class);
		register(LeerlingEndpoint.class);
	}
}
