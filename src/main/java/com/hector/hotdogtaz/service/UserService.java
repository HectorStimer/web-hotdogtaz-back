package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.dto.request.CreateUserDTO;
import com.hector.hotdogtaz.dto.request.UpdateUserDTO;
import com.hector.hotdogtaz.dto.response.UserResponseDTO;
import com.hector.hotdogtaz.model.User;
import com.hector.hotdogtaz.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    public final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository=repository;
    }


    private void validateEmail(String email){
        if(repository.existsByEmail((email))){
            throw new RuntimeException("This email Already exists");

        }
    }



    public User save(CreateUserDTO dto){
        logger.info("Creating a new User");
        validateEmail(dto.email());
        User user = new User(
                dto.name(),
                dto.email(),
                dto.password(),
                dto.active(),
                dto.type());

        return repository.save(user);
    }




    public User update(UpdateUserDTO dto, Long id){
        logger.info("Update user {}",+id);
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("this user cannot found"));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setActive(dto.active());
        return  repository.save(user);
    }




    public void deactivate(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("this user cannot exists"));
        user.setActive(false);
        repository.save(user);
    }




    public List<UserResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.isActive(),
                        user.getCreatedAt(),
                        user.getType()))
                .toList();
    }




    public UserResponseDTO listById(Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("This user cannot found"));

        return new UserResponseDTO(user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getCreatedAt(),
                user.getType());
    }



    public List<UserResponseDTO> listByStatus(Boolean active){
        return repository.findByActiveTrue().stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.isActive(),
                        user.getCreatedAt(),
                        user.getType())
                )
                .toList();
    }




}
