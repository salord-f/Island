package fr.unice.polytech.si3.qgl.isg.resources;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ResourceTest {

	@Test
	public void NameTest() {
		Arrays.stream(Resource.values())
				.forEach(resource -> assertEquals(resource, Resource.fromString(resource.name())));
	}

	@Test
	public void BiomeTest() {
		Arrays.stream(Resource.values())
				.forEach(resource -> assertFalse(resource.getConcernedBiomes().isEmpty()));
	}

	@Test
	public void TypeTest() {
		Arrays.stream(Resource.values())
				.forEach(resource -> assertEquals(Type.PRIMARY, resource.getType()));
	}
}
