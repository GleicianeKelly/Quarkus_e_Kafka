package org.br.mineradora.scheduler;

import io.quarkus.scheduler.Scheduled;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.transaction.Transactional;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationScheduler {


    private final Logger log = LoggerFactory.getLogger(KafkaEvents.class);

    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    void schedule(){
        log.info("-- Executando scheduler --");
        quotationService.getCurrencyPriceInfo();
    }
}
