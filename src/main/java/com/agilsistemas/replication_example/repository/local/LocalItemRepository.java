package com.agilsistemas.replication_example.repository.local;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilsistemas.replication_example.model.Item;

public interface LocalItemRepository extends JpaRepository<Item, Integer> {
    
}
