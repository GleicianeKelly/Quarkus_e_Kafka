package org.br.mineradora.controller;

import org.br.mineradora.dto.ProposalDetailsDTO;
import org.br.mineradora.service.ProposalService;
import org.eclipse.microprofile.openapi.models.servers.Server;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/api/trade")
public class ProposalController {

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public Response getProposalDetailsById(@PathParam("id") long id){
        try{
            return Response.ok(proposalService.getProposalDetailsById(id)
                    ,MediaType.APPLICATION_JSON).build();
        }catch (ServerErrorException errorException){
            return Response.serverError().build();
        }
    }


   @POST
   @RolesAllowed("proposal-customer")
   public Response createNewProposal(ProposalDetailsDTO proposalDetails){
        int proposalRequestStatus = proposalService.createProposal(proposalDetails).getStatus();

        if(proposalRequestStatus > 199 || proposalRequestStatus < 205){
            return Response.ok().build();
        }else {
            return Response.status(proposalRequestStatus).build();
        }
   }


   @DELETE
   @Path("/remove/{id}")
   @RolesAllowed("manager")
   public Response removeProposal(@PathParam("id") long id){
        int proposalRequestStatus = proposalService.removeProposal(id).getStatus();

       if(proposalRequestStatus > 199 || proposalRequestStatus < 205){
           return Response.ok().build();
       }else {
           return Response.status(proposalRequestStatus).build();
       }
   }

}
