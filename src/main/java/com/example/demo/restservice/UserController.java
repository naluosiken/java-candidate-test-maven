package com.example.demo.restservice;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.vo.CreateUserRequestVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create")
    public UserDto greeting(@RequestBody CreateUserRequestVo createUserRequestVo) {

        User targetUser = modelMapper.map(createUserRequestVo, User.class);

        List<User> results = userRepository.findById1AndId2(targetUser.getId1(), targetUser.getId2());

        if(results.size() > 0){
            return modelMapper.map(results.get(0), UserDto.class);
        }else{
            userRepository.save(targetUser);
            return modelMapper.map(targetUser, UserDto.class);
        }
    }

}
