package edu.supavenir.ormtest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.supavenir.ormtest.models.Organization;

@Repository
public interface OrgaRepository extends JpaRepository<Organization, Integer> {

}
