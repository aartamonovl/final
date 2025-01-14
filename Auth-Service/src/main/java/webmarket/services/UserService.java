package webmarket.services;

import webmarket.entities.Role;
import webmarket.entities.User;
import webmarket.repositories.RoleRepository;
import webmarket.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for working with system users.
 */
@Service
@Data
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /**
     * User repository.
     */
    private final UserRepository userRepository;

    /**
     * Repository of user roles.
     */
    private final RoleRepository roleRepository;

    /**
     * Searches for a user in the repository by his name.
     * @param username
     * @return
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Creates a new user with roles.
     * @param user
     * @param role
     */
    @Transactional
    public void createNewUser(User user, String role){
        User u = userRepository.save(user);
        Role r = roleRepository.findRoleByName(role);
        u.getRoles().add(r);
        userRepository.save(u);
    }

    /**
     * Selects all users.
     * @return
     */
    public List<User> getAll(){
        return userRepository.getAll();
    }

    /**
     * Deletes a user by Id.
      * @param id
     */
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    /**
     * Retrieves user data by name.
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}