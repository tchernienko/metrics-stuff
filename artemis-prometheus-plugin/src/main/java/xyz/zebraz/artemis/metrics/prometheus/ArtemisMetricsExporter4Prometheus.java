package xyz.zebraz.artemis.metrics.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apache.activemq.artemis.core.server.metrics.ActiveMQMetricsPlugin;

import java.io.Serializable;
import java.util.Map;

/**
 * My simple implementation to export artemis metrics to prometheus using micrometer.io.
 * This is to be used as a artemis plugin. see http://localhost:8161/user-manual/metrics.html for more on this.
 */
public class ArtemisMetricsExporter4Prometheus implements ActiveMQMetricsPlugin, Serializable {

    private MeterRegistry meterRegistry;

    /**
     * {@inheritDoc}
     *
     * The options map contains key/value pairs config of the plugin declared in broker.xml.
     */
    public ActiveMQMetricsPlugin init(final Map<String, String> options) {

/*
        ClassLoader cl = ClassLoader.getSystemClassLoader();
//((ClassLoaders.AppClassLoader)cl).getPackages()
        URL[] urls = ((URLClassLoader)cl).getURLs();

        System.out.println("==================== artemis classpath ====================");
        for(URL url: urls){
            System.out.println(url.getFile());
        }
*/

        PrometheusConfig prometheusConfig = new PrometheusConfig() {

            /**
             *
             * @param s the name of the parameter containing the prometheus instance
             * @return the url of the prometheus instance to register against
             */
            public String get(String s) {
                return options.get(s);
            }
        };

        meterRegistry = new PrometheusMeterRegistry(prometheusConfig);
        return this;
    }

    /**
     *
     * @return the registry
     */
    public MeterRegistry getRegistry() {
        return meterRegistry;
    }
}
