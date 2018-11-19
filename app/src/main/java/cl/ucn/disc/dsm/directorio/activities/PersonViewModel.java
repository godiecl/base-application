/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.activities;

import android.app.Application;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import cl.ucn.disc.dsm.directorio.model.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonViewModel extends AndroidViewModel {

    /**
     * Json un-serializer
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * List of person.
     */
    private MutableLiveData<List<Person>> people;

    /**
     * @param application to use.
     */
    public PersonViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * @return the LiveData of List of Person.
     */
    public LiveData<List<Person>> getPeople() {

        if (people == null) {
            log.debug("People is null, loading ..");
            this.people = new MutableLiveData<>();
            loadPeople();
        }

        return this.people;
    }

    /**
     *
     */
    private void loadPeople() {

        Handler handler = new Handler();
        handler.post(() -> {

            log.debug("Loading data ..");

            List<Person> personList;

            try (final InputStream is = getApplication().getAssets().open("ucn.json")) {

                // Tipo de la lista
                final Type listType = new TypeToken<List<Person>>() {
                }.getType();

                // Lector
                final Reader reader = new InputStreamReader(is);

                // People!
                personList = GSON.fromJson(reader, listType);

            } catch (final IOException ex) {
                log.error("Error, ex");
                return;
            }

            // Set the list
            this.people.setValue(personList);

        });

    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    @Override
    protected void onCleared() {
        this.people = null;
    }
}
