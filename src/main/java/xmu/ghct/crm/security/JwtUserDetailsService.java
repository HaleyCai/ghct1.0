package xmu.ghct.crm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.LoginUserVO;
import xmu.ghct.crm.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SongLingbing
 * @date 2018/12/24 8:55
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        LoginUserVO user=userService.getUserByUserAccount(account);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", account));
        } else {
            List<String> roles=new ArrayList<>();
            roles.add(user.getRole());
            return new JwtUser(user.getAccount(), user.getPassword(),roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
    }
}
