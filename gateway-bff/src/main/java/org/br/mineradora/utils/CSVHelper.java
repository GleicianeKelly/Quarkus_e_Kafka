package org.br.mineradora.utils;

import org.apache.commons.csv.CSVPrinter;
import org.br.mineradora.dto.OpportunityDTO;

import org.apache.commons.csv.CSVFormat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {

    public static ByteArrayInputStream opportunitiesToCSV(List<OpportunityDTO> opportunities){

        final CSVFormat format = CSVFormat.DEFAULT.withHeader("ID Proposta", "Cliente", "Preço por tonelada", "Melhor cotação de moeda");

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);
            for(OpportunityDTO oops : opportunities){
                List<String> data = Arrays.asList(String.valueOf(oops.getProposalId()), oops.getCustomer(),
                        String.valueOf(oops.getPriceTonne()), String.valueOf(oops.getLastDollarQuotation()));

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e){
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}

