package it.fox;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class Dashboard extends AppLayout {

    private static final long serialVersionUID = -8903543948372546656L;

    public Dashboard() {
        Image img = new Image("img/logo.png", "Pietro Rimoldi Logo");
        img.setHeight("64px");
        addToNavbar(new DrawerToggle());
        HorizontalLayout horizontalLogo = new HorizontalLayout(img);
        horizontalLogo.setWidthFull();
        horizontalLogo.setJustifyContentMode(JustifyContentMode.CENTER);
        HorizontalLayout horizontalHome = new HorizontalLayout( new Icon(VaadinIcon.COG), new Text("Home"));
        horizontalHome.setWidth("100%");
        horizontalHome.setAlignItems(Alignment.CENTER);
        horizontalHome.setJustifyContentMode(JustifyContentMode.START);
        Tab tabHome = new Tab(horizontalHome);
        Tabs tabs = new Tabs(tabHome, new Tab("About"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(horizontalLogo, tabs);
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        horizontalLayout.setAlignItems(Alignment.END);
        horizontalLayout.add(new Button("First Button"), new DateTimePicker(), new TextField("Text Field"), new DateTimePicker(), new Button("Second Button"));
        main.add(horizontalLayout, new Grid<>());
        setContent(main);
    }
}
