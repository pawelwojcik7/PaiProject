package com.pai.project.repository;

import com.pai.project.entity.AppUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByLogin(String login);

    @Modifying
    @Query("update app_user set password = :password  where login = :login")
    void updateUserDataWithNewPassword(
            @Param("login") String login,
            @Param("password") String password);


    @Modifying
    @Query("update app_user set subscription = :subscription  where login = :login")
    void updateUserDataWithNewSubscription(
            @Param("login") String login,
            @Param("subscription") String subscription);

}
