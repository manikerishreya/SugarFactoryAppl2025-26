package com.project.model;

import com.project.entity.OfficerEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class OfficerPrinciple implements UserDetails {

    private OfficerEntity officerEntity; // the actual wrapped object

    public OfficerPrinciple(Optional<OfficerEntity> officerEntity) {
        // unwrap the Optional safely
        this.officerEntity = officerEntity
                .orElseThrow(() -> new RuntimeException("Officer not found"));
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
////        return Collections.singleton(new SimpleGrantedAuthority("OFFICER"));
//        return Collections.singleton(new SimpleGrantedAuthority(officerEntity.getRole()));
//
//    }

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(
            new SimpleGrantedAuthority("ROLE_" + officerEntity.getRole())
    );
}


    @Override
    public String getPassword() {
        return officerEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return officerEntity.getUsername();
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
}
