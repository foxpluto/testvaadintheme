package it.fox;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Meta;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Viewport("width=device-width, initial-scale=1, viewport-fit=cover")
@PageTitle("A cool vaadin app")
@BodySize(height = "100vh", width = "100vw")
@Meta(name = "author", content = "bunny")
@PWA(name = "Cool Vaadin App", shortName = "my-app")
@Push(value = PushMode.MANUAL, transport = Transport.WEBSOCKET)
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/main.css")
public class AppShell implements AppShellConfigurator {

    private static final long serialVersionUID = 5667536054512022404L;

    @Override
    public void configurePage(AppShellSettings settings) {
        // Nothing to add here
    }

}
