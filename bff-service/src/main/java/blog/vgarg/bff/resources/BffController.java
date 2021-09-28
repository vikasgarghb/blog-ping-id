package blog.vgarg.bff.resources;

import blog.vgarg.bff.representation.InfoRepresentation;
import blog.vgarg.bff.services.BffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bff")
public class BffController {

    private final BffService bffService;

    public BffController(BffService bffService) {
        this.bffService = bffService;
    }

    @GetMapping
    @ResponseBody
    public InfoRepresentation getInfo() {
        return bffService.getInfo();
    }

    @PostMapping("/todo")
    public ResponseEntity addTodo(@RequestBody String todo) {
        bffService.addTodo(todo);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/tweet")
    public ResponseEntity addTweet(@RequestBody String tweet) {
        bffService.addTweet(tweet);
        return new ResponseEntity(HttpStatus.OK);
    }
}
