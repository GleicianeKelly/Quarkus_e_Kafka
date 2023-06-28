package org.br.mineradora.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "proposal")
@Data
@NoArgsConstructor
public class ProposalEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String customer;

    @Column(name = "price_tone")
    private BigDecimal priceTonne;

    private String country;

    private Integer tonnes;

    @Column(name = "proposal_validity_days")
    private Integer proposalValidityDays;

    private Date created;
}
