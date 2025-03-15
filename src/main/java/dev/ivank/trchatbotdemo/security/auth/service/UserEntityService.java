package dev.ivank.trchatbotdemo.security.auth.service;

import dev.ivank.trchatbotdemo.security.auth.dao.UserDao;
import dev.ivank.trchatbotdemo.security.auth.domain.User;
import dev.ivank.trchatbotdemo.common.AbstractEntityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService extends AbstractEntityService<User, Long, UserDao> implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return dao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("%s not found.".formatted(email)));
    }
}
