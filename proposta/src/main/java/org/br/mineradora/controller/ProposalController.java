package org.br.mineradora.controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.quarkus.security.Authenticated;
import org.br.mineradora.dto.ProposalDetailsDTO;
import org.br.mineradora.message.KafkaEvent;
import org.br.mineradora.service.ProposalService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/proposal")
@Authenticated
public class ProposalController {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    ProposalService proposalService;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public ProposalDetailsDTO findDetailsProposal(@PathParam("id") long id){
        return proposalService.findFullProposal(id);
    }

    @POST
    @RolesAllowed("proposal-customer") //o usuario que pode criar uma proposta
    public Response createProposal(ProposalDetailsDTO proposalDetails){
        LOG.info("-- Recebendo proposat de compra --");

        try{

            proposalService.createNewProposal(proposalDetails);
            return Response.ok().build();
        } catch (Exception e ){
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response removeProposal(long id){
        try{
            proposalService.removeProposal(id);
            return Response.ok().build();
        } catch (Exception e){
           return Response.serverError().build();
        }
    }

}
