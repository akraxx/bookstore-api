package fr.flst.jee.mmarie.views;

import io.dropwizard.views.View;

import java.nio.charset.Charset;

/**
 * Created by Maximilien on 06/11/2014.
 */
public class WebappView extends View {

    public WebappView() {
        super("/index.ftl", Charset.forName("UTF-8"));
    }
}
