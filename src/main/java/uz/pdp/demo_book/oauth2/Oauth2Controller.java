package uz.pdp.demo_book.oauth2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Oauth2Controller {

    @GetMapping("/oauth2/authorization/google")
    public String google(){
        return "auth2";
    }
}
