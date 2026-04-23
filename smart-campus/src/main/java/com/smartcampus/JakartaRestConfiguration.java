/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus;

import com.smartcampus.filters.ApiLoggingFilter;
import com.smartcampus.mappers.GlobalExceptionMapper;
import com.smartcampus.mappers.LinkedResourceNotFoundExceptionMapper;
import com.smartcampus.mappers.RoomNotEmptyExceptionMapper;
import com.smartcampus.mappers.SensorUnavailableExceptionMapper;
import com.smartcampus.resources.DiscoveryResource;
import com.smartcampus.resources.RoomResource;
import com.smartcampus.resources.SensorResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * JAX-RS Application Configuration class.
 * Manually registers all resource classes, exception mappers,etc..
 */
public class JakartaRestConfiguration extends Application {
    
    /**
     * Returns the set of all JAX-RS components to be registered.
     * This includes resource classes, exception mappers and filters.
     * @return Set of Class objects representing all registered components
     */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

                // Register resource classes (API endpoints)
		classes.add(DiscoveryResource.class);
		classes.add(RoomResource.class);
		classes.add(SensorResource.class);

                // Register exception mappers (error handling)
		classes.add(RoomNotEmptyExceptionMapper.class);
		classes.add(LinkedResourceNotFoundExceptionMapper.class);
		classes.add(SensorUnavailableExceptionMapper.class);
		classes.add(GlobalExceptionMapper.class);

                // Register filters (logging)
		classes.add(ApiLoggingFilter.class);
		return classes;
	}
}
