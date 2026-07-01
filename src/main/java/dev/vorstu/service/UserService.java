package dev.vorstu.service;

import dev.vorstu.dto.user.UserRequestDto;
import dev.vorstu.dto.user.UserResponseDto;
import dev.vorstu.entity.User;
import dev.vorstu.exceptions.NotFoundException;
import dev.vorstu.mapper.UserMapper;
import dev.vorstu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser(UserRequestDto dto){
        User newUser = userMapper.toEntity(dto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User savedUser = userRepository.save(newUser);
        return userMapper.toDto(savedUser);
    }

    public UserResponseDto getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public Page<UserResponseDto> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Transactional
    public UserResponseDto changeUser(UserRequestDto dto, Long id){
        User changingUser = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        userMapper.updateEntityFromDto(changingUser, dto);
        if (dto.getPassword()!=null){
            changingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        User changedUser = userRepository.save(changingUser);
        return userMapper.toDto(changedUser);
    }

    @Transactional
    public UserResponseDto deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        userRepository.delete(user);
        return userMapper.toDto(user);
    }
}
