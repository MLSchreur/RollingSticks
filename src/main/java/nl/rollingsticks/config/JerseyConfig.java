package nl.rollingsticks.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import nl.rollingsticks.rest.service.TekstEndpoint;
import nl.rollingsticks.rest.service.MuziekstukEndpoint;
import nl.rollingsticks.rest.service.GroepEndpoint;
import nl.rollingsticks.rest.service.HuiswerkopdrachtEndpoint;
import nl.rollingsticks.rest.service.LeerlingEndpoint;
import nl.rollingsticks.rest.service.DocentEndpoint;
import nl.rollingsticks.rest.service.LoginEndpoint;
import nl.rollingsticks.rest.service.ParseXMLEndpoint;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(){
		register(TekstEndpoint.class);
		register(MuziekstukEndpoint.class);
		register(GroepEndpoint.class);
		register(HuiswerkopdrachtEndpoint.class);
		register(LeerlingEndpoint.class);
		register(DocentEndpoint.class);
		register(LoginEndpoint.class);
		register(ParseXMLEndpoint.class);
	}
}