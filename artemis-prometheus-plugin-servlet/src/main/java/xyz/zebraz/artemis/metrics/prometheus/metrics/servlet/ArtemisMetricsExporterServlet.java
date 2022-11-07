package xyz.zebraz.artemis.metrics.prometheus.metrics.servlet;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@WebServlet(name = "ArtemisMetricsExporterServlet", urlPatterns = "/metrics")
public class ArtemisMetricsExporterServlet extends HttpServlet {

    private PrometheusMeterRegistry registry;
    private static final Logger LOG = LoggerFactory.getLogger(ArtemisMetricsExporterServlet.class);

    public ArtemisMetricsExporterServlet() {
        LOG.info("ArtemisMetricsExporterServlet constructor");
        Set<MeterRegistry> registries = Metrics.globalRegistry.getRegistries();
        if (registries != null && registries.size() > 0) {
            registry = (PrometheusMeterRegistry) registries.toArray()[0];
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        if (LOG.isTraceEnabled() ) {
            LOG.trace("ArtemisMetricsExporterServlet doGet");
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        if (registry == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Prometheus meter registry is null. Has the Prometheus Metrics Plugin been configured?");
        } else {
            try (Writer writer = resp.getWriter()) {
                writer.write(registry.scrape());
                writer.flush();
            }
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

}
