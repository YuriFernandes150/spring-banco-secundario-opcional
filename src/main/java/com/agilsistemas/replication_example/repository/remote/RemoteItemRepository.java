package com.agilsistemas.replication_example.repository.remote;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilsistemas.replication_example.model.Item;

/**
 * Esse repositório lida com as operações de banco com a tblitens remoto.
 * Ele foi definido para o banco local na classe de config local (RemoteDBConfig). 
 * @author YuriFernandes150
 */
public interface RemoteItemRepository extends JpaRepository<Item, Integer> {
    
}
