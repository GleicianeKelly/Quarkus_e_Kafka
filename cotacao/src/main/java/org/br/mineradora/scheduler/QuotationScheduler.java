package org.br.mineradora.scheduler;

import io.quarkus.scheduler.Scheduled;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.br.mineradora.service.QuotationService;

@ApplicationScoped
public class QuotationScheduler {


    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    void schedule(){
        quotationService.getCurrencyPriceInfo();
    }
}
