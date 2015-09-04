package worlds;

import helpers.WorldHelper;

import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.syngenio.vaadin.synergy.SynergyView;
import de.syngenio.vaadin.synergy.layout.AbstractSynergyLayoutFactory.Packing;
import de.syngenio.vaadin.synergy.layout.VerticalSynergyLayoutFactory;

@Theme("default")
@WorldDescription(prose="demonstrates a single vertical sidebar of large navigation icons", tags={"vertical", "stacked"})
public class WorldOfImageSidebarNavigationUI extends UI
{
    @WebServlet(value = "/vertical/images/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = WorldOfImageSidebarNavigationUI.class)
    public static class Servlet extends VaadinServlet {
    }

    private static final Logger log = LoggerFactory.getLogger(WorldOfImageSidebarNavigationUI.class);
    
    @Override
    protected void init(VaadinRequest request)
    {
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSizeFull();
        SynergyView synergyView = new SynergyView(new VerticalSynergyLayoutFactory(Packing.EXPAND), WorldHelper.getImageNavigation2());
//        synergyView.setSizeUndefined();
        synergyView.setWidth("200px");
        synergyView.setHeight("100%");
        hlayout.addComponent(synergyView);
        hlayout.setExpandRatio(synergyView, 0f);
        
        Panel panel = new Panel();
        panel.setSizeFull();
        panel.setId("content");
        hlayout.addComponent(panel);
        hlayout.setExpandRatio(panel, 1f);

        setContent(hlayout);
        
        setNavigator(new Navigator(this, panel));
        final NavigationView genericView = new NavigationView();
        getNavigator().addView("", genericView);
        getNavigator().addView("view", genericView);
    }
    
    private static class NavigationView extends CustomComponent implements View {
        private Label label;
        private Image image;
        
        private NavigationView() {
            VerticalLayout vlayout = new VerticalLayout();
            setCompositionRoot(vlayout);
            label = new Label("(empty)");
            vlayout.addComponent(label);
        }
        @Override
        public void enter(ViewChangeEvent event)
        {
            label.setValue(event.getParameters());
        }
    }
}
