package mz.edu.osoma.javacompiler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CodingExamController {


    @GetMapping(value = "/")
    public String coding(){
        return "code-exam";
    }
}
