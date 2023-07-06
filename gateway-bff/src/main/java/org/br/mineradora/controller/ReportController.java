package org.br.mineradora.controller;

import org.br.mineradora.service.ProposalService;
import org.br.mineradora.service.ReportService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.Data;
import java.util.Date;

@Path("/api/opportunity")
@ApplicationScoped
public class ReportController {

    @Inject
    ReportService reportService;

    @GET
    @Path("/report")
    @RolesAllowed({"user", "manager"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatedReport(){

        try{
            return Response.ok(reportService.generateCSVOpportunityReport(),
                    MediaType.APPLICATION_OCTET_STREAM).header("content-disposition",
                    "attachment; filename = " + new Date() + "--oportunidades-venda.csv").build();
        }catch (ServerErrorException errorException){
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/data")
    @RolesAllowed({"user", "manager"})
    public Response generatedOpportunitiesData(){
        try{
            return Response.ok(reportService.getOpportunityData(),
                    MediaType.APPLICATION_JSON).build();
        }catch (ServerErrorException errorException){
            return Response.serverError().build();
        }
    }



}
