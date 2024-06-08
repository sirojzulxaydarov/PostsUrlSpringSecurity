package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

private final UserRepository userRepository;

    public FileController(UserRepository userRepository, HttpServletResponse httpServletResponse) {
        this.userRepository = userRepository;

    }

    @Transactional
    @GetMapping("/{id}")
    public void getProfileImg(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        User user = userRepository.findById(id).get();
        response.getOutputStream().write(user.getAttachment().getContent());

    }



}
