package it.fox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Route("")
public class Dashboard extends AppLayout {

    private static final long serialVersionUID = -8903543948372546656L;
    private static Logger logger = LogManager.getLogger();

    private Button button = new Button("Start long-running task");
    private ProgressBar progressBar = new ProgressBar();
    private Paragraph message = new Paragraph();

    /**
     * Main dashboard
     */
    public Dashboard() {
        button.setWidth("15em");
        progressBar.setWidth("15em");
        
            Image img = new Image("icons/logo.jpg", "Pietro Rimoldi Logo");
            img.setHeight("64px");
            addToNavbar(new DrawerToggle());
            HorizontalLayout horizontalLogo = new HorizontalLayout(img);
            horizontalLogo.setWidthFull();
            horizontalLogo.setJustifyContentMode(JustifyContentMode.CENTER);
            HorizontalLayout horizontalHome = new HorizontalLayout( new Icon(VaadinIcon.COG), new Text("  Home"));
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

    private void startLongRunningTask() {
        logger.info("Start Long running Task");
        button.setEnabled(false);
        progressBar.setVisible(true);
        message.setText("Please wait...");
        UI ui = UI.getCurrent(); // get the instance before running a new thread

        ui.setPollInterval(200);
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                logger.error("Thread stopped error", e);
            }
            return "finish";
        }).thenAccept(status -> ui.access(() -> {
                                    logger.info("Returned State is: {}", status);
                                    button.setEnabled(true);
                                    progressBar.setVisible(false);
                                    message.setText("Task completed.");
                                    ui.setPollInterval(-1);
                                })
        );
    }
}
