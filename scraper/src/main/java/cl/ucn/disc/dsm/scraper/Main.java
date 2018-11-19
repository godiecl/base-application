package cl.ucn.disc.dsm.scraper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Getting the data from http://online.ucn.cl/directoriotelefonicoemail/Default.aspx?ind=func
 */
@Slf4j
public final class Main {


    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {

        log.debug("Main starting ..");

        // Json un-serializer
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        // List of person
        final List<Person> people = new ArrayList<>();

        // Ids
        int ini = 27265;
        int end = 27503;
        int part = 50;
        for (int id = ini; id < end; id++) {

            if (id == 27370) {
                continue;
            }

            // Get the information of Person with id i
            Person person = getInformacion(id);

            // If we found informacion, added to list
            if (person != null) {
                people.add(person);
            }

            // Patience!
            try {
                Thread.sleep(150 + (int) (150 * Math.random()));
            } catch (InterruptedException e) {
                // Nothing here
            }

            // Saving 100 to 100
            if (people.size() == part) {

                String filename = "people_" + id + ".json";
                FileUtils.writeStringToFile(new File(filename), gson.toJson(people), StandardCharsets.UTF_8);
                log.debug("Writed {} persons in {}.", part, filename, people.size());

                people.clear();

            }

        }

        String filename = "people_last.json";
        FileUtils.writeStringToFile(new File(filename), gson.toJson(people), StandardCharsets.UTF_8);
        log.debug("Writed in {} {} persons.", filename, people.size());


        /*
        getInformacion(25773);
        getInformacion(142);
        getInformacion(9990);
        */

        log.debug("Main ended!");

    }

    /**
     * @param id
     * @return Persona
     */
    private static Person getInformacion(final int id) throws IOException {

        // log.debug("Processing id: {}", id);

        Document doc = Jsoup.connect("http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=" + id).get();
        // log.debug("Title: {}", doc.title());

        String nombre = getString(doc, "lblNombre");
        // log.debug("Nombre: {}", nombre);

        if (nombre.length() < 2) {
            log.debug("Person id {} not found!", id);
            return null;
        }

        String cargo = getString(doc, "lblCargo");
        // log.debug("Cargo: {}", cargo);

        String unidad = getString(doc, "lblUnidad");
        // log.debug("Unidad: {}", unidad);

        String email = getString(doc, "lblEmail");
        // log.debug("Email: {}", email);

        String telefono = getString(doc, "lblTelefono");
        // log.debug("Telefono: {}", telefono);

        String oficina = getString(doc, "lblOficina");
        // log.debug("Oficina: {}", oficina);

        Person persona = Person.builder()
                .id(id)
                .nombre(nombre)
                .cargo(cargo)
                .unidad(unidad)
                .email(email)
                .telefono(telefono)
                .oficina(oficina)
                .build();

        log.debug("Id: {}. Nombre: {}", id, persona.getNombre());

        return persona;

    }

    /**
     * @param doc
     * @param id
     * @return the String
     */
    private static String getString(final Document doc, final String id) {
        Element element = doc.getElementById(id);
        return element.text();

    }

}
