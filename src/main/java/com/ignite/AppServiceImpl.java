package com.ignite;

import java.time.LocalTime;
import java.util.List;

import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignite.model.Student;

@Service
public class AppServiceImpl implements AppService {

	private static final String CACHE_NAME = "StudentCache";

	@Autowired
	private IgniteClient igniteClient;

	@Override
	public void get(Long key) {
		ClientCache<Long, Student> cache = igniteClient.getOrCreateCache(CACHE_NAME);

		LocalTime startTime = LocalTime.now();

		Student cachedVal = cache.get(key);

		LocalTime endTime = LocalTime.now();
		System.out.println("[loadCache] Time pre: " + startTime);
		System.out.println("[loadCache] Time pos: " + endTime);
		System.out.println("[loadCache] Time diff: " + (endTime.getNano() - startTime.getNano()));

		System.out.format(">>> Loaded [%s] from the cache.\n", cachedVal);
		System.out.println("\n\n Values on cache [Name]: " + cache.getName());
	}

	@Override
	public void getAll() {
		ClientCache<Long, Student> cache = igniteClient.getOrCreateCache(CACHE_NAME);

		LocalTime startTime = LocalTime.now();

		// Execute query on cache.
        QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery(
                "select id, name from Student"));

		LocalTime endTime = LocalTime.now();
		System.out.println("[loadCache] Time pre: " + startTime);
		System.out.println("[loadCache] Time pos: " + endTime);
		System.out.println("[loadCache] Time diff: " + (endTime.getNano() - startTime.getNano()));

		// After the time prints now proceed to print the results
		System.out.println(cursor.getAll());
	}
}
