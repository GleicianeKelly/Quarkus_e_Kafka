package org.br.mineradora.client;

import io.quarkus.oidc.token.propagation.reactive.AccessTokenRequestReactiveFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.br.mineradora.dto.OpportunityDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import java.util.List;

@Path("/api/opportunity")
@RegisterRestClient
@RegisterProvider(AccessTokenRequestReactiveFilter.class)
@ApplicationScoped
public interface ReportRestClient {

    List<OpportunityDTO> requestOpportunitiesData();

}
