package com.agilsistemas.replication_example.repository.remote;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilsistemas.replication_example.model.Item;

public interface RemoteItemRepository extends JpaRepository<Item, Integer> {
    
}
