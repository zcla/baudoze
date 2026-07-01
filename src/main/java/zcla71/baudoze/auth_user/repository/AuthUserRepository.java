package zcla71.baudoze.auth_user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zcla71.baudoze.auth_user.entity.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
	Optional<AuthUser> findByProviderAndSubject(String provider, String subject);
}
