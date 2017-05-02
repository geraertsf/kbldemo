package lu.kbl.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(lu.kbl.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(lu.kbl.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Category.class.getName() + ".funds", jcacheConfiguration);
            cm.createCache(lu.kbl.domain.SubCategory.class.getName(), jcacheConfiguration);
            cm.createCache(lu.kbl.domain.SubCategory.class.getName() + ".funds", jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Currency.class.getName(), jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Fund.class.getName(), jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Fund.class.getName() + ".vnis", jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Fund.class.getName() + ".countries", jcacheConfiguration);
            cm.createCache(lu.kbl.domain.VniHistory.class.getName(), jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(lu.kbl.domain.Country.class.getName() + ".funds", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
