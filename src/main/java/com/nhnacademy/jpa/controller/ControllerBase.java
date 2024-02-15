package com.nhnacademy.jpa.controller;

import com.nhnacademy.jpa.entity.Resident;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ControllerBase {

    @ModelAttribute(name = "user")
    default Resident getUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session == null){
            return null;
        }

        return (Resident) session.getAttribute("user");
    }
}
