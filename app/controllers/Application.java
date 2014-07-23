package controllers;

import play.libs.EventSource;
import play.libs.F;
import play.libs.Time;
import play.mvc.*;

import java.util.Date;


public class Application extends Controller {

    // no for loop needed
    public static Result index() {
        final EventSource eventSource = EventSource.whenConnected(new F.Callback<EventSource>() {


            @Override
            public void invoke(EventSource eventSource) throws Throwable {
                final Date now = new Date(System.currentTimeMillis());
                eventSource.send(new EventSource.Event("{\"data\":\""+ now.toString()+"\"}","id","message"));
                /*eventSource.send(new EventSource.Event("{\"data\":1}", "id1", "message"));
                eventSource.send(new EventSource.Event("{\"data\":2}", "id1", "message"));
                eventSource.send(new EventSource.Event("{\"data\":3}", "id1", "message"));
                eventSource.send(new EventSource.Event("{\"data\":4}", "id1", "message"));
                eventSource.send(new EventSource.Event("closed!", "id1", "close"));*/
                //eventSource.send(new EventSource.Event("closed!", "id1", "close"));
                eventSource.close();
            }

        });
        return ok(eventSource).as("text/event-stream");
    }

    public static Result index2(){
        return ok(new EventSource(){
            @Override
            public void onConnected() {
                final Date now = new Date(System.currentTimeMillis());
                for (int i = 0; i < 20; i++) {
                    send(new Event("{\"data\":\""+ now.toString()+"\"}","id","message"));
                }

               close();
            }
        }).as("text/event-stream");
    }


    public static Result ssePage(){
        return ok(views.html.sse.render("sse start page"));
    }

}
