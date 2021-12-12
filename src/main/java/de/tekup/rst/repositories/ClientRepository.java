package de.tekup.rst.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import de.tekup.rst.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
