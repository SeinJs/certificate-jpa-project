package com.nhnacademy.jpa.controller;

import com.nhnacademy.jpa.entity.Resident;
import com.nhnacademy.jpa.repository.ResidentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * web에게 필요한 데이터 반영하여 view를 반환
 */
@Slf4j
@Controller
@RequestMapping("/")
public class ResidentController{
    private final ResidentRepository residentRepository;

    public ResidentController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @GetMapping
    public String getAllResidents(Pageable pageable, Model model){
        List<Resident> residentList = residentRepository.findAll(pageable).getContent();

        model.addAttribute("residentList", residentList);
        return "index";
    }
}
