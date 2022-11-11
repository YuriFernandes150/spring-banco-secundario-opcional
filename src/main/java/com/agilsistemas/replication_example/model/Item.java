package com.agilsistemas.replication_example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Essa tabela de itens está presente em ambos os bancos, mas não precisamos mapear dois objetos.
 * @author YuriFernandes150
 */
@Entity
@Table(name = "tbitens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sqitens", initialValue = 1, sequenceName = "sqitens")
    @Column(name = "id_item", nullable = false)
    int idItem;

    @Column(name = "item")
    String descricaoItem;

    @Override
    public String toString() {
        return descricaoItem;
    }

}
