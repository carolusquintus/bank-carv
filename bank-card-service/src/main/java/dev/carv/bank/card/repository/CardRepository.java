package dev.carv.bank.card.repository;

import dev.carv.bank.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {
}
