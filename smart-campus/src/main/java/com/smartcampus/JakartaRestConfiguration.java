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
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
public class JakartaRestConfiguration extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

		classes.add(DiscoveryResource.class);
		classes.add(RoomResource.class);
		classes.add(SensorResource.class);

		classes.add(RoomNotEmptyExceptionMapper.class);
		classes.add(LinkedResourceNotFoundExceptionMapper.class);
		classes.add(SensorUnavailableExceptionMapper.class);
		classes.add(GlobalExceptionMapper.class);

		classes.add(ApiLoggingFilter.class);
		return classes;
	}
}
