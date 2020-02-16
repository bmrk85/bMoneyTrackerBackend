package hu.bmrk.bmoneytrackerbackend.service;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findByUname(name);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }else{
            return new User(user.getUname(), user.getPassword(), Collections.emptyList());//TODO: role mapping
        }
    }

    public UserEntity save(UserEntity user){
        UserEntity newUser = new UserEntity();
        newUser.setUname(user.getUname());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userEntityRepository.save(newUser);
    }

}
