package com.updatetech.SpringRestAPI.ui.service;


import com.updatetech.SpringRestAPI.ui.Dto.UserDto;
import com.updatetech.SpringRestAPI.ui.Dto.Utils;
import com.updatetech.SpringRestAPI.ui.exception.UserServiceException;
import com.updatetech.SpringRestAPI.ui.model.response.ErrorMessages;
import com.updatetech.SpringRestAPI.ui.repository.UserRepository;
import com.updatetech.SpringRestAPI.ui.userEntiry.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId= utils.genarateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));

       UserEntity storedUser = userRepository.save(userEntity);
        System.out.println("this is user service userId: "+storedUser.getUserId());
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUser, returnValue);

        return returnValue;
    }

    @Override
    public UserDto findUser(String email) {
        UserEntity userEntity = userRepository.findUserByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto findUserByUserId(String id) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findUserByUserId(id);
        if (userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserDto returnValue = new UserDto();

        UserEntity userEntity = userRepository.findUserByUserId(userId);
        if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        UserEntity updateUser = userRepository.save(userEntity);
        BeanUtils.copyProperties(updateUser,returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findUserByUserId(id);

        if (userEntity == null) throw  new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        if (page>0)page= page-1;

        Pageable pageableRequest = PageRequest.of(page,limit);
        Page<UserEntity> userEntities = userRepository.findAll(pageableRequest);

        List<UserEntity> userEntityList = userEntities.getContent();
        for (UserEntity userEntity: userEntityList){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
