package org.spring.security2.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.spring.security2.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class MyUserDetailsImpl implements UserDetails , OAuth2User {

    private Map<String, Object> getAttributes;

    private MemberEntity memberEntity;

    public MyUserDetailsImpl(MemberEntity memberEntity){
        this.memberEntity=memberEntity;
    }

    public MyUserDetailsImpl(MemberEntity memberEntity, Map<String, Object> getAttributes) {
        this.memberEntity=memberEntity;
        this.getAttributes=getAttributes;

    }





    @Override
    public Map<String, Object> getAttributes() {
        return getAttributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectRoles=new ArrayList<>();
        collectRoles.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_"+memberEntity.getRole().toString();
            }
        });

        return collectRoles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    @Override
    public String getPassword() {
        return memberEntity.getUserPw();
    }

    @Override
    public String getUsername() {
        return memberEntity.getUserEmail();
    }

    @Override
    public String getName() {
        return memberEntity.getUserName();
    }

    public Long getId() {
        return memberEntity.getId();
    }
}
