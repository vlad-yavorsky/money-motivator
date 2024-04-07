package com.bepsik.moneymotivator.entity;

import com.bepsik.moneymotivator.enumeration.BalanceOperation;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_balance_history")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BalanceHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_balance_history_id_seq")
    @SequenceGenerator(name = "t_balance_history_id_seq", sequenceName = "t_balance_history_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private BalanceOperation operation;

    private BigDecimal value;

    @Column(insertable = false, updatable = false)
    private LocalDateTime date;

    private BigDecimal remaining;

}
