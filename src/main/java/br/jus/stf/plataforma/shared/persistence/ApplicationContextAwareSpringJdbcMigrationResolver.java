/**
 * Copyright 2010-2015 Axel Fontaine
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.jus.stf.plataforma.shared.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.migration.MigrationChecksumProvider;
import org.flywaydb.core.api.migration.MigrationInfoProvider;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.resolver.MigrationInfoHelper;
import org.flywaydb.core.internal.resolver.ResolvedMigrationComparator;
import org.flywaydb.core.internal.resolver.ResolvedMigrationImpl;
import org.flywaydb.core.internal.resolver.spring.SpringJdbcMigrationExecutor;
import org.flywaydb.core.internal.resolver.spring.SpringJdbcMigrationResolver;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.Pair;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.context.ApplicationContext;

/**
 * Migration resolver for {@link SpringJdbcMigration}s which are registered in the given {@link ApplicationContext}.
 * This resolver provides the ability to use other beans registered in the {@link ApplicationContext} and reference
 * them via Spring's dependency injection facility inside the {@link SpringJdbcMigration}s.
 */
public class ApplicationContextAwareSpringJdbcMigrationResolver extends SpringJdbcMigrationResolver {

    private final ApplicationContext applicationContext;

    /**
     * Creates a new instance.
     *
     * @param classLoader        The ClassLoader for loading migrations on the classpath.
     * @param location           The base package on the classpath where to migrations are located.
     * @param applicationContext The Spring application context to load the {@link SpringJdbcMigration}s from.
     */
    public ApplicationContextAwareSpringJdbcMigrationResolver(ClassLoader classLoader, Location location,
                                                              ApplicationContext applicationContext) {
        super(classLoader, location);
        this.applicationContext = applicationContext;
    }

    @Override
    public Collection<ResolvedMigration> resolveMigrations() {
        // get all beans of type SpringJdbcMigration from the application context
        Map<String, SpringJdbcMigration> springJdbcMigrationBeans =
                (Map<String, SpringJdbcMigration>) this.applicationContext.getBeansOfType(SpringJdbcMigration.class);

        ArrayList<ResolvedMigration> resolvedMigrations = new ArrayList<ResolvedMigration>();

        // resolve the migration and populate it with the migration info
        for (SpringJdbcMigration springJdbcMigrationBean : springJdbcMigrationBeans.values()) {
            ResolvedMigrationImpl resolvedMigration = extractJdbcMigrationInfo(springJdbcMigrationBean);
            resolvedMigration.setPhysicalLocation(ClassUtils.getLocationOnDisk(springJdbcMigrationBean.getClass()));
            resolvedMigration.setExecutor(new SpringJdbcMigrationExecutor(springJdbcMigrationBean));

            resolvedMigrations.add(resolvedMigration);
        }

        Collections.sort(resolvedMigrations, new ResolvedMigrationComparator());
        return resolvedMigrations;
    }
    
    /**
     * Duplicate method because the one from SpringJdbcMigrationResolver is not visible here.
     * 
     * Extracts the migration info from this migration.
     *
     * @param springJdbcMigration The migration to analyse.
     * @return The migration info.
     */
    private ResolvedMigrationImpl extractJdbcMigrationInfo(SpringJdbcMigration springJdbcMigration) {
        Integer checksum = null;
        if (springJdbcMigration instanceof MigrationChecksumProvider) {
            MigrationChecksumProvider checksumProvider = (MigrationChecksumProvider) springJdbcMigration;
            checksum = checksumProvider.getChecksum();
        }

        MigrationVersion version;
        String description;
        if (springJdbcMigration instanceof MigrationInfoProvider) {
            MigrationInfoProvider infoProvider = (MigrationInfoProvider) springJdbcMigration;
            version = infoProvider.getVersion();
            description = infoProvider.getDescription();
            if (!StringUtils.hasText(description)) {
                throw new FlywayException("Missing description for migration " + version);
            }
        } else {
            Pair<MigrationVersion, String> info =
                    MigrationInfoHelper.extractVersionAndDescription(
                            ClassUtils.getShortName(springJdbcMigration.getClass()), "V", "__", "");
            version = info.getLeft();
            description = info.getRight();
        }

        String script = springJdbcMigration.getClass().getName();

        ResolvedMigrationImpl resolvedMigration = new ResolvedMigrationImpl();
        resolvedMigration.setVersion(version);
        resolvedMigration.setDescription(description);
        resolvedMigration.setScript(script);
        resolvedMigration.setChecksum(checksum);
        resolvedMigration.setType(MigrationType.SPRING_JDBC);
        return resolvedMigration;
    }

}