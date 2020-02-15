package com.updatetech.SpringRestAPI.ui.repository;

import com.updatetech.SpringRestAPI.ui.userEntiry.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findUserByEmail(String email);

    UserEntity findUserByUserId(String id);
}
