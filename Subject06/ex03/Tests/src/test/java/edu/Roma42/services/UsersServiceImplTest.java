package edu.Roma42.services;

import	edu.Roma42.models.User;
import	edu.Roma42.repositories.UsersRepository;
import edu.Roma42.repositories.UsersRepository;
import main.java.edu.Roma42.services.UsersServiceImpl;
import	edu.Roma42.exceptions.AlreadyAuthenticatedException;
import	org.junit.jupiter.api.Test;
import static	org.junit.jupiter.api.Assertions.assertTrue;
import static	org.junit.jupiter.api.Assertions.assertFalse;
import static	org.junit.jupiter.api.Assertions.assertThrows;
import static	org.mockito.Mockito.mock;
import static	org.mockito.Mockito.doThrow;
import static	org.mockito.Mockito.when;
import static	org.mockito.Mockito.times;
import static	org.mockito.Mockito.verify;
import static	org.mockito.Mockito.verifyNoMoreInteractions;
import static	org.mockito.Mockito.never;

public class UsersServiceImplTest {

	@Test
	public void TestCorrectLogins() {

		UsersRepository mockrepo = mock(UsersRepository.class);
		User mlongo = new User(1L, "mlongo", "ciao", false);
		User lnicoter = new User(2L, "lnicoter", "1234", true);
		User fcarlucc = new User(3L, "fcarlucc", "4321", false);
		when(mockrepo.findByLogin("mlongo")).thenReturn(mlongo);
		when(mockrepo.findByLogin("lnicoter")).thenReturn(lnicoter);
		when(mockrepo.findByLogin("fcarlucc")).thenReturn(fcarlucc);
		doThrow(new RuntimeException("")).when(mockrepo).update(fcarlucc);
		UsersServiceImpl UsersService = new UsersServiceImpl(mockrepo);
		assertTrue(UsersService.authenticate("mlongo", "ciao"));
		assertThrows(AlreadyAuthenticatedException.class, () -> UsersService.authenticate("lnicoter", "1234"));
		assertFalse(UsersService.authenticate("fcarlucc", "4321"));
		verify(mockrepo).findByLogin("mlongo");
		verify(mockrepo).findByLogin("lnicoter");
		verify(mockrepo).findByLogin("fcarlucc");
		verify(mockrepo, times(1)).update(mlongo);
		verify(mockrepo, never()).update(lnicoter);
		verify(mockrepo, times(1)).update(fcarlucc);
		verifyNoMoreInteractions(mockrepo);
	}

	@Test
	public void TestIncorrectPassword() {

		UsersRepository mockrepo = mock(UsersRepository.class);
		User mlongo = new User(1L, "mlongo", "ciao", false);
		when(mockrepo.findByLogin("mlongo")).thenReturn(mlongo);
		UsersServiceImpl UsersService = new UsersServiceImpl(mockrepo);
		assertFalse(UsersService.authenticate("mlongo", "ciap"));
		verify(mockrepo).findByLogin("mlongo");
		verify(mockrepo, never()).update(mlongo);
		verifyNoMoreInteractions(mockrepo);
	}
}
