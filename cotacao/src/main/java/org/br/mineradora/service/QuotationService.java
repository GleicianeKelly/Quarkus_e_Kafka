package org.br.mineradora.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.br.mineradora.client.CurrencyPriceClient;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class QuotationService {

    @Inject
    @RestClient
    CurrencyPriceClient currencyPriceClient;

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    KafkaEvents kafkaEvents;

    public void getCurrencyPriceInfo(){
        CurrencyPriceDTO currencyPriceInfo = currencyPriceClient.getPriceByPair("USD-BRL");

        if(updateCurrenceInfoPrice(currencyPriceInfo)){
            kafkaEvents.senNewKafkaEvent(QuotationDTO
                    .builder()
                    .currencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
                    .date(new Date())
                    .build());
        }
    }

    private boolean updateCurrenceInfoPrice(CurrencyPriceDTO currencyPriceInfo){
        BigDecimal currentPrice = new BigDecimal(currencyPriceInfo.USDBRL.getBid());
        boolean updatePrice = false;

        List<QuotationEntity> quotationList = quotationRepository.findAll().list();

        if (quotationList.isEmpty()){
            saveQuotation(currencyPriceInfo);
            updatePrice = true;
        }else{

            QuotationEntity lastDollarPrice = quotationList
                    .get(quotationList.size() - 1);

            if(currentPrice.floatValue() > lastDollarPrice.getCurrencyPrice().floatValue()){
                updatePrice = true;
                saveQuotation(currencyPriceInfo);
            }
        }
        return updatePrice;
    }

    private void saveQuotation(CurrencyPriceDTO currencyInfo){
        QuotationEntity quotation = new QuotationEntity();

        quotation.setDate(new Date());
        quotation.setCurrencyPrice(new BigDecimal(currencyInfo.USDBRL.getBid()));
        quotation.setPctChange(currencyInfo.getUSDBRL().getPctChange());
        quotation.setPair("USD-BRL");

        quotationRepository.persist(quotation);
    }
}
