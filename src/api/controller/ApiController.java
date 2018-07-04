package api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApiController {
    @RequestMapping(value = "recipies", method = RequestMethod.GET)
    public ModelAndView recipies(HttpServletRequest rq) {
        return new ModelAndView("home");
    }
}