package xyz.zebraz.artemis.metrics.prometheus.metrics.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(MyContextListener.class);


    public MyContextListener() {
        LOG.info("ServletContextListener constructor trace");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.info("Initialized {} plugin", this.getClass().getSimpleName());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Metrics webapp context destroyed.");
    }
}
