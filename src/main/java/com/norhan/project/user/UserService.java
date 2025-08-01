package com.norhan.project.user;
import com.norhan.project.dtos.ChangePasswordRequest;
import com.norhan.project.dtos.RegisterUserRequest;
import com.norhan.project.dtos.UpdateUserRequest;
import com.norhan.project.dtos.UserDto;
import com.norhan.project.mappers.UserMapper;
import com.norhan.project.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
@AllArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Iterable<UserDto> getAllUsers(String sortBy) {
        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }


    public UserDto registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException();
        }
        System.out.printf(request.toString());
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.USER);
        userRepository.save(user);

        return userMapper.toDto(user);
    }
    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userMapper.updateUser(request, user);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public void deleteUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AccessDeniedException("Password does not match");
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
}
