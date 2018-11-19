/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public final class ThreadScrapper {

    // Start id
    private static final int START_ID = 1;

    // End id
    private static final int END_ID = 27503;

    // Parallel tasks
    private static final int PARALLEL_TASKS = 4;

    // Saving block
    private static final int BLOCK_SIZE = 50;

    // Json un-serializer
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        // Init!
        final long startMillis = System.currentTimeMillis();

        // List
        final List<Person> list = Collections.synchronizedList(new ArrayList<>());

        // Counter
        final AtomicInteger id = new AtomicInteger(START_ID);

        // Parallel level
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        log.debug("Available Processors: {}", availableProcessors);

        // The Executor
        final ExecutorService executor = Executors.newWorkStealingPool(availableProcessors);

        for (int i = 0; i < PARALLEL_TASKS; i++) {

            // Execute the task!
            executor.execute(getTask(id, list));

        }

        // Try to shutdown
        executor.shutdown();

        log.debug("Waiting for termination ..");
        executor.awaitTermination(1, TimeUnit.HOURS);
        log.debug("Ended in {} ms.", (System.currentTimeMillis()) - startMillis);


    }

    /**
     * @param id
     * @param list
     * @return
     */
    private static Runnable getTask(AtomicInteger id, List<Person> list) {

        final Runnable task = () -> {

            while (true) {

                try {

                    // Value
                    final int value = id.getAndIncrement();

                    // Condicion de salida
                    if (value > END_ID) {
                        return;
                    }

                    // Web documento (webpage)
                    final Document doc = Jsoup
                            .connect("http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=" + value)
                            .get();

                    // Name of the person
                    final String nombre = getString(doc, "lblNombre");

                    String threadName = Thread.currentThread().getName();
                    log.debug("Thread: {} searching id: {} -> {}", threadName, value, nombre);

                    if (nombre.length() < 2) {
                        // log.debug("Person id {} not found!", value);
                        continue;
                    }

                    final String cargo = getString(doc, "lblCargo");
                    final String unidad = getString(doc, "lblUnidad");
                    final String email = getString(doc, "lblEmail");
                    final String telefono = getString(doc, "lblTelefono");
                    final String oficina = getString(doc, "lblOficina");

                    final Person person = Person.builder()
                            .id(value)
                            .nombre(nombre)
                            .cargo(cargo)
                            .unidad(unidad)
                            .email(email)
                            .telefono(telefono)
                            .oficina(oficina)
                            .build();

                    // log.debug("Id: {}. Nombre: {}", value, persona.getNombre());

                    // Add person to list
                    list.add(person);

                    // If we have the block size? save!
                    synchronized (list) {

                        // Block size
                        if (list.size() == BLOCK_SIZE) {

                            // Filename
                            final String filename = "people_" + value + ".json";
                            log.debug("Saving {} persons in {}.", BLOCK_SIZE, filename);

                            FileUtils.writeStringToFile(new File(filename), GSON.toJson(list), StandardCharsets.UTF_8);

                            list.clear();
                        }
                    }

                } catch (final IOException e) {
                    log.error("Error", e);
                }


                try {
                    TimeUnit.MILLISECONDS.sleep(500 + (int) (1000 * Math.random()));
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                }

            } // end while


        };

        return task;


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
