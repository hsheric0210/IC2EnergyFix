package com.eric0210.ic2energyfix.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import ic2.core.block.machine.tileentity.TileEntityStandardMachine;

public final class ReflectionHelper
{
	private static final String MODIFIERS_FIELD_NAME = "modifiers";

	private static <T> void tamperFinalField(final Field finalField, final Object instance, final T value) throws NoSuchFieldException, IllegalAccessException
	{
		// Set field accessible
		if (!finalField.isAccessible())
			finalField.setAccessible(true);

		// Remove final access modifier
		removeFinalAccessFlag(finalField);

		// Tamper the field
		finalField.set(instance, value);
	}

	public static <T> void tamperFinalField(final Class<?> clazz, final String fieldName, final Object instance, final T value)
	{
		try
		{
			tamperFinalField(clazz.getDeclaredField(fieldName), instance, value);
		}
		catch (final IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException e)
		{
			throw new IllegalArgumentException("[IC2EnergyFix] [ReflectionHelper] Can't tamper final field " + clazz.getName() + "." + fieldName, e);
		}
	}

	private static void removeFinalAccessFlag(final Member member) throws NoSuchFieldException, IllegalAccessException
	{
		final Field modifiers = member.getClass().getDeclaredField(MODIFIERS_FIELD_NAME);
		modifiers.setAccessible(true);
		modifiers.setInt(member, member.getModifiers() & ~Modifier.FINAL);
	}

	private static <T> T readField(final Field field, final Object instance) throws IllegalAccessException
	{
		// Set field accessible
		if (!field.isAccessible())
			field.setAccessible(true);

		// Read the field value
		return (T) field.get(instance);
	}

	private static <T> T readField(final Class<?> clazz, final String fieldName, final Object instance)
	{
		try
		{
			return readField(clazz.getDeclaredField(fieldName), instance);
		}
		catch (final IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException e)
		{
			throw new IllegalStateException("[IC2EnergyFix] [ReflectionHelper] Can't read field " + clazz.getName() + "." + fieldName, e);
		}
	}

	public static void setOperationLength(final Object instance, final int newOperationLength)
	{
		tamperFinalField(TileEntityStandardMachine.class, "defaultOperationLength", instance, newOperationLength);
		tamperFinalField(TileEntityStandardMachine.class, "operationLength", instance, newOperationLength);
	}

	private ReflectionHelper()
	{
	}
}
