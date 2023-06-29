package org.br.mineradora.message;

import javax.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.QuotationDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class  KafkaEvents {

    private final Logger log = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationRequestEmitter;

    public void senNewKafkaEvent(QuotationDTO quotation){
        log.info("-- Enviando Cotação para Tópico Kafka --");
        quotationRequestEmitter.send(quotation).toCompletableFuture().join();
    }
}
