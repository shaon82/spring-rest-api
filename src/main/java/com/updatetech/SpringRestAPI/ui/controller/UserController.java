package com.updatetech.SpringRestAPI.ui.controller;


import com.updatetech.SpringRestAPI.ui.Dto.UserDto;
import com.updatetech.SpringRestAPI.ui.exception.UserServiceException;
import com.updatetech.SpringRestAPI.ui.model.request.UserDetailsRequestModel;
import com.updatetech.SpringRestAPI.ui.model.response.*;
import com.updatetech.SpringRestAPI.ui.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/get-user/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.findUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }


    @PostMapping(value = "/save-user", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
                                     , produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel)throws Exception{
        UserRest returnValue = new UserRest();

        if (userDetailsRequestModel.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages());
        if (userDetailsRequestModel.getLastName().isEmpty()) throw new NullPointerException("Last name could not be empty!!!");

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetailsRequestModel, userDto);
        UserDto createUser = userService.createUser(userDto);

        System.out.println("this is user controller userID: "+createUser.getUserId());


        BeanUtils.copyProperties(createUser,returnValue);
        return returnValue;
    }



    @PutMapping(value = "/update-user/{userId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
                                                , produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest updateUser(@PathVariable("userId") String userId, @Valid @RequestBody UserDetailsRequestModel userDetailsRequestModel){
                UserRest returnValue = new UserRest();

                UserDto userDto = new UserDto();

                BeanUtils.copyProperties(userDetailsRequestModel, userDto);
                UserDto userDto1 = userService.updateUser(userId, userDto);
                BeanUtils.copyProperties(userDto1, returnValue);
        return returnValue;
    }


    @DeleteMapping(path = "/delete/user/{id}",
                    produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable("id") String id){

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }


    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getAllUser(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "limit", defaultValue = "20") int limit){
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page,limit);

        for (UserDto userDto: users){
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            returnValue.add(userRest);
        }
        return returnValue;
    }
}
