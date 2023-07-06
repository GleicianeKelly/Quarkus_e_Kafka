package org.br.mineradora.service;

import org.br.mineradora.client.ReportRestClient;
import org.br.mineradora.dto.OpportunityDTO;
import org.br.mineradora.utils.CSVHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
public class ReportServiceImpl implements ReportService{

    @Inject
    ReportRestClient reportRestClient;

    @Override
    public ByteArrayInputStream generateCSVOpportunityReport() {

        List<OpportunityDTO> opportunityData = reportRestClient.requestOpportunitiesData();
        return CSVHelper.opportunitiesToCSV(opportunityData);
    }

    @Override
    public List<OpportunityDTO> getOpportunityData() {
        return reportRestClient.requestOpportunitiesData();
    }
}
