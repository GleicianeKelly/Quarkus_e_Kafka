package org.br.mineradora.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.ProposalDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvent {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvent.class);

    @Channel("proposal")
    Emitter<ProposalDTO> proposalRequesterEmitter;


    public void sendNewKafkaEvent(ProposalDTO proposalDTO){
        LOG.info("-- Enviando Nova Proposta para on Tópico Kafka --");
        proposalRequesterEmitter.send(proposalDTO).toCompletableFuture().join();
    }

}
