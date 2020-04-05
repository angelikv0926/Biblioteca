package com.ceiba.biblioteca.dominio.servicio.bibliotecario;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class ServicioBibliotecario {

    public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
    public static final String EL_LIBRO_NO_EXISTE = "El libro no existe";
    public static final String EL_LIBRO_ES_PALINDROMO = "los libros palíndromos solo se pueden utilizar en la biblioteca";

    private final RepositorioLibro repositorioLibro;
    private final RepositorioPrestamo repositorioPrestamo;

    public ServicioBibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioPrestamo = repositorioPrestamo;
    }

    public void prestar(String isbn, String nombreUsuario) {
        //Busqueda del libro por ibsn
        Libro libro = repositorioLibro.obtenerPorIsbn(isbn);

        if(libro == null){
            //Validacion si el libro existe
            throw new PrestamoException(EL_LIBRO_NO_EXISTE);
        } if(validarPalindromo(isbn)) {
            //Valida si el isbn del libro es palindromo
            throw new PrestamoException(EL_LIBRO_ES_PALINDROMO);
        }
        //Declara fecha de solicitud (fecha del sistema)
        Date fechaSolicitus = new Date();
        //Calcula fecha de entrega
        Date fechaEntrega = calcularFechaEntrega(fechaSolicitus, isbn);

        //Crea prestamo
        Prestamo prestamo = new Prestamo(fechaSolicitus, libro, fechaEntrega, nombreUsuario);
        repositorioPrestamo.agregar(prestamo);
    }

    public boolean esPrestado(String isbn) {
        return repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn) != null;
    }

    public boolean validarPalindromo(String isbn) {
        StringBuilder sb = new StringBuilder(isbn);
        //Aplicar reverso al isbn
        String isbnReverse = sb.reverse().toString();
        //retorna verdadero si son iguales (palindromo) de lo contrario retorna false
        return isbn.equals(isbnReverse);
    }

    public Date calcularFechaEntrega(Date fechaSolicitud, String isbn) {
        //Calcular suma de valores por ISBN
        int valor = Pattern.compile("[^0-9]").matcher(isbn).replaceAll("").chars().map(i -> Character.getNumericValue(i)).sum();
        Calendar fechaEntrega = new GregorianCalendar();
        fechaEntrega.setTime(fechaSolicitud);

        if(valor > 30){
            //cliclo de 15 dias
            for (int i = 0; i < 14; i++) {
                if(fechaEntrega.get(Calendar.DAY_OF_WEEK) == 1){
                    //Si el dia es domingo no lo tiene en cuenta
                    fechaEntrega.add(Calendar.DAY_OF_YEAR, 1);
                }
                //Suma un dia
                fechaEntrega.add(Calendar.DAY_OF_YEAR, 1);
            }
            //Si el dia final es domingo pasa al siguiente dia
            if(fechaEntrega.get(Calendar.DAY_OF_WEEK) == 1){
                fechaEntrega.add(Calendar.DAY_OF_YEAR, 1);
            }
            //Retorna fecha final si la suma es mayor a 30
            return fechaEntrega.getTime();
        } else {
            //Retorna nulo si la suma es mayor a 30
            return null;
        }
    }
}
