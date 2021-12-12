package de.tekup.rst.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tekup.rst.entities.Ticket;


public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
