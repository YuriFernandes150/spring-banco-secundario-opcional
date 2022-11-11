package com.agilsistemas.replication_example.repository.local;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilsistemas.replication_example.model.Item;

/**
 * Esse repositório lida com as operações de banco com a tblitens local.
 * Ele foi definido para o banco local na classe de config local (LocalDBConfig). 
 * @author YuriFernandes150
 */
public interface LocalItemRepository extends JpaRepository<Item, Integer> {
    
}
