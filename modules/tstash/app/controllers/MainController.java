package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import service.DbService;

public class MainController extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render(DbService.testDb()));
    }
    
}
