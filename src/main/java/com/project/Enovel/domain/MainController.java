package com.project.Enovel.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/main")
    public String mainPage() {
        // 메서드 로직
        return "main/main";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/main";
    }
<<<<<<< HEAD:src/main/java/com/project/Enovel/RootUrlController.java

    @GetMapping("/main")
    public String main() {
        return "main/main";
    }
=======
>>>>>>> 2517e00c1a2ae0414f5f01d81eca8cb0a84eeb3c:src/main/java/com/project/Enovel/domain/MainController.java
}
