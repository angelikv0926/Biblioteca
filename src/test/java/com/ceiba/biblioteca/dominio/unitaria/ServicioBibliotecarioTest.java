package com.ceiba.biblioteca.dominio.unitaria;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import com.ceiba.biblioteca.testdatabuilder.LibroTestDataBuilder;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBibliotecarioTest {

    private static final String ISBN_LIBRO_12421 = "12421";
    private static final String ISBN_LIBRO_PD9999 = "PD9999";

    @Test
    public void libroYaEstaPrestadoTest() {
        // arrange
        Libro libro = new LibroTestDataBuilder().build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean existeProducto = servicioBibliotecario.esPrestado(libro.getIsbn());

        //assert
        assertTrue(existeProducto);
    }

    @Test
    public void libroNoEstaPrestadoTest() {
        // arrange
        Libro libro = new LibroTestDataBuilder().build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean existeProducto = servicioBibliotecario.esPrestado(libro.getIsbn());

        //assert
        assertFalse(existeProducto);
    }

    @Test
    public void validarPalindromoTest() {
        // arrange
        LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
        Libro libro = libroTestDataBuilder.conisbn(ISBN_LIBRO_12421).build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean validaPalindromo = servicioBibliotecario.validarPalindromo(libro.getIsbn());
        System.out.println("ISBN: " + libro.getIsbn());
        System.out.println("Valida Palindromo: " + validaPalindromo);

        //assert
        assertTrue(validaPalindromo);
    }

    @Test
    public void validarNOPalindromoTest() {
        // arrange
        LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
        Libro libro = libroTestDataBuilder.conisbn(ISBN_LIBRO_PD9999).build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean validaPalindromo = servicioBibliotecario.validarPalindromo(libro.getIsbn());
        System.out.println("ISBN: " + libro.getIsbn());
        System.out.println("Valida Palindromo: " + validaPalindromo);

        //assert
        assertFalse(validaPalindromo);
    }

    @Test
    public void calcularFechaEntregaTest() {
        // arrange
        LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
        Libro libro = libroTestDataBuilder.conisbn(ISBN_LIBRO_PD9999).build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        Date fechaSolicitud = Calendar.getInstance().getTime();

        // act
        Date fechaEntrega = servicioBibliotecario.calcularFechaEntrega(fechaSolicitud, libro.getIsbn());
        System.out.println("Fecha entrega calculada: " + fechaEntrega);
        //assert
        assertNotNull(fechaEntrega);
    }

    @Test
    public void calcularNOFechaEntregaTest() {
        // arrange
        LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
        Libro libro = libroTestDataBuilder.conisbn(ISBN_LIBRO_12421).build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        Date fechaSolicitud = Calendar.getInstance().getTime();

        // act
        Date fechaEntrega = servicioBibliotecario.calcularFechaEntrega(fechaSolicitud, libro.getIsbn());
        System.out.println("Fecha entrega calculada: " + fechaEntrega);
        //assert
        assertNull(fechaEntrega);
    }

    @Test
    public void validarFechaEntregaDiaTest() {
        // arrange
        LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
        Libro libro = libroTestDataBuilder.conisbn(ISBN_LIBRO_PD9999).build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        Calendar fechaSolicitud = Calendar.getInstance();
        fechaSolicitud.set(2020,3,3);

        Calendar fechaEntregaFinal = Calendar.getInstance();
        fechaEntregaFinal.set(2020,3,20);

        // act
        Date fechaEntrega = servicioBibliotecario.calcularFechaEntrega(fechaSolicitud.getTime(), libro.getIsbn());
        System.out.println("Fecha entrega final: " + fechaEntregaFinal.getTime());
        System.out.println("Fecha entrega calculada: " + fechaEntrega);
        //assert
        assertEquals(fechaEntregaFinal.getTime(), fechaEntrega);
    }

    @Test
    public void validarFechaEntregaDomingoTest() {
        // arrange
        LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
        Libro libro = libroTestDataBuilder.conisbn(ISBN_LIBRO_PD9999).build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        Calendar fechaSolicitud = Calendar.getInstance();
        fechaSolicitud.set(2020,3,5);

        Calendar fechaEntregaFinal = Calendar.getInstance();
        fechaEntregaFinal.set(2020,3,22);

        // act
        Date fechaEntrega = servicioBibliotecario.calcularFechaEntrega(fechaSolicitud.getTime(), libro.getIsbn());
        System.out.println("Fecha entrega final: " + fechaEntregaFinal.getTime());
        System.out.println("Fecha entrega calculada: " + fechaEntrega);
        //assert
        assertEquals(fechaEntregaFinal.getTime(), fechaEntrega);
    }
}

