package webmarket.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webmarket.entities.Role;

/**
 * Repository for working with user roles.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     * Selects a role by the name of the role.
     * @param role
     * @return
     */
    @Query("select r from Role r where r.name = ?1")
    Role findRoleByName(String role);
}
