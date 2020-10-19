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

        String id1 = createUserRequestVo.getId1();
        String id2 = createUserRequestVo.getId2();

        List<User> results = userRepository.findById1AndId2(id1, id2);

        if(results.size() > 0){
            return modelMapper.map(results.get(0), UserDto.class);
        }else{
            User user = new User();
            user.setId1(createUserRequestVo.getId1());
            user.setId2(createUserRequestVo.getId2());

            userRepository.save(user);

            return modelMapper.map(user, UserDto.class);
        }
    }

}
