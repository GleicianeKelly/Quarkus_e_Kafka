package org.br.mineradora.controller;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.br.mineradora.dto.ProposalDetailsDTO;
import org.br.mineradora.message.KafkaEvent;
import org.br.mineradora.service.ProposalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/proposal")
public class ProposalController {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    public ProposalDetailsDTO findDetailsProposal(@PathParam("id") long id){
        return proposalService.findFullProposal(id);
    }

    @POST
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
    public Response removeProposal(long id){
        try{
            proposalService.removeProposal(id);
            return Response.ok().build();
        } catch (Exception e){
           return Response.serverError().build();
        }
    }

}
