package com.he.engelund.api;

import com.he.engelund.dto.UserDto;
import com.he.engelund.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;
import static com.he.engelund.config.ModelMapperConfig.SET_TYPE_USER_DTO;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    private ModelMapper mm;

    private UserService userService;

    @GetMapping("/get-all")
    Set<UserDto> getUsers() {
        var users = userService.getUsers();
        return mm.map(users, SET_TYPE_USER_DTO);
    }
}
