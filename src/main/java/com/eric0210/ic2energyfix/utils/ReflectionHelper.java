package com.eric0210.ic2energyfix.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionHelper
{
	private static final String MODIFIERS_FIELD_NAME = "modifiers";

	public static <T> void tamperFinalField(final Field finalField, final Object instance, final T value)
	{
		try
		{
			// Set field accessible
			if (!finalField.isAccessible())
				finalField.setAccessible(true);

			// Remove final access modifier
			removeFinalAccessFlag(finalField);

			// Tamper the field
			finalField.set(instance, value);
		}
		catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e)
		{
			e.printStackTrace();
		}
	}

	public static <T> void tamperFinalField(final Class<?> clazz, final String fieldName, final Object instance, final T value)
	{
		try
		{
			tamperFinalField(clazz.getDeclaredField(fieldName), instance, value);
		}
		catch (final IllegalArgumentException | NoSuchFieldException | SecurityException e)
		{
			new IllegalStateException("Can't tamper final field " + fieldName + " in class " + clazz.getName(), e).printStackTrace();
		}
	}

	private static void removeFinalAccessFlag(final Member member) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		final Field modifiers = member.getClass().getDeclaredField(MODIFIERS_FIELD_NAME);
		modifiers.setAccessible(true);
		modifiers.setInt(member, member.getModifiers() & ~Modifier.FINAL);
	}
}
