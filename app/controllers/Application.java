package controllers;

import play.libs.EventSource;
import play.libs.F;
import play.mvc.*;


public class Application extends Controller {

    public static Result index() {
        final EventSource eventSource = EventSource.whenConnected(new F.Callback<EventSource>() {

            @Override
            public void invoke(EventSource eventSource) throws Throwable {
                eventSource.send(new EventSource.Event("{\"data\":1}", "id1", "message"));
                eventSource.send(new EventSource.Event("{\"data\":2}", "id1", "message"));
                eventSource.send(new EventSource.Event("{\"data\":3}", "id1", "message"));
                eventSource.send(new EventSource.Event("{\"data\":4}", "id1", "message"));
                eventSource.send(new EventSource.Event("closed!", "id1", "close"));
                eventSource.close();
            }
        });
        return ok(eventSource).as("text/event-stream");
    }


    public static Result ssePage(){
        return ok(views.html.sse.render("sse start page"));
    }

}
