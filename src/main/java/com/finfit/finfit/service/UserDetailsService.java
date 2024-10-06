package com.finfit.finfit.service;

import com.finfit.finfit.model.UserPrinciples;
import com.finfit.finfit.model.User;
import com.finfit.finfit.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class FinfitUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user=usersRepository.findByUsername(username);
       if(user==null){
           System.out.println("user not found");
           throw new UsernameNotFoundException(username);
       }

       return new UserPrinciples(user);
    }
}
