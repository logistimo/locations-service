

package com.logistimo.locations.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.logistimo.locations.entity.location.Country;
import com.logistimo.locations.entity.location.District;
import com.logistimo.locations.entity.location.State;
import com.logistimo.locations.entity.location.SubDistrict;
import com.logistimo.locations.repository.location.CountryRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Component
public class LocationLoader {

    final static String NAME = "name";
    final static String STATES = "states";
    final static String TALUK = "taluks";
    final static String USER = "location-user";

    @Resource
    CountryRepository repository;


    public void load() throws IOException {

        org.springframework.core.io.Resource resource = new ClassPathResource("location.json");
        Reader fileReader = new InputStreamReader(resource.getInputStream());
        JsonParser parser = new JsonParser();
        JsonObject countries = parser.parse(fileReader).getAsJsonObject().get("data").getAsJsonObject();
        Iterator itr = countries.entrySet().iterator();

        //Country
        Country country = null;
        Set<State> states = null;
        while (itr.hasNext()) {

            country = new Country();
            states = new HashSet<>();
            String ccode = null;
            JsonObject cobject = null;
            Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) itr.next();
            ccode = entry.getKey();
            cobject = entry.getValue().getAsJsonObject();
            Iterator citr = cobject.entrySet().iterator();
            String cname = null;
            while (citr.hasNext()) {

                Map.Entry<String, JsonElement> centry = (Map.Entry<String, JsonElement>) citr.next();
                JsonObject sobject = null;
                Iterator sitr = null;
                String sname = null;
                JsonObject dobject = null;
                if (NAME.equals(centry.getKey()) && centry.getValue().isJsonPrimitive()) {
                    cname = centry.getValue().getAsString();
                    country.setCode(ccode);
                    country.setName(cname);
                    country.setCreatedBy(USER);
                    country.setCreatedOn(new Date());
                }

                if (STATES.equals(centry.getKey()) && centry.getValue().isJsonObject()) {
                    if (null != cname && ("Switzerland".equals(cname))) {
                        continue;
                    }
                    sobject = centry.getValue().getAsJsonObject();
                    sitr = sobject.entrySet().iterator();
                    State state = null;
                    while (sitr.hasNext()) {
                        Map.Entry<String, JsonElement> sentry = (Map.Entry<String, JsonElement>) sitr.next();
                        sname = sentry.getKey();
                        state = new State();
                        state.setName(sname);
                        state.setCreatedBy(USER);
                        state.setCreatedOn(new Date());
                        state.setCountry(country);
                        states.add(state);
                        dobject =
                                sentry.getValue().getAsJsonObject().entrySet().iterator().next().getValue()
                                        .getAsJsonObject();
                        Iterator ditr = dobject.entrySet().iterator();
                        String dname = null;
                        District district = null;
                        Set<District> districts = new HashSet<>();
                        while (ditr.hasNext()) {
                            Map.Entry<String, JsonElement> dentry = (Map.Entry<String, JsonElement>) ditr.next();
                            dname = dentry.getKey();
                            district = new District();
                            district.setName(dname);
                            district.setCreatedBy(USER);
                            district.setCreatedOn(new Date());
                            district.setState(state);

                            Set<SubDistrict> subDistricts = null;
                            if (dentry.getValue().getAsJsonObject() != null
                                    && dentry.getValue().getAsJsonObject().entrySet().size() > 0) {
                                if (dentry.getValue().getAsJsonObject().entrySet().iterator().next().getKey().equalsIgnoreCase(TALUK)) {
                                    JsonArray
                                            tarray =
                                            dentry.getValue().getAsJsonObject().entrySet().iterator().next().getValue()
                                                    .getAsJsonArray();
                                    subDistricts = populateSubDistricts(district, tarray);
                                    if (subDistricts.size() > 0) {
                                        district.setSubDistricts(subDistricts);
                                    }
                                }
                            }
                            districts.add(district);
                        }
                        if (districts.size() > 0)
                            state.setDistricts(districts);
                    }
                }
            }
            if (states.size() > 0)
                country.setStates(states);
            //saving to database
            repository.save(country);
        }

    }

    Set<SubDistrict> populateSubDistricts(District district, JsonArray jArray) {

        Set<SubDistrict> subDistricts = new HashSet<>();
        SubDistrict subDistrict = null;
        if (jArray != null) {
            for (int i = 0; i < jArray.size(); i++) {
                subDistrict = new SubDistrict();
                subDistrict.setName(jArray.get(i).getAsString());
                subDistrict.setCreatedBy(USER);
                subDistrict.setCreatedOn(new Date());
                subDistrict.setDistrict(district);
                subDistricts.add(subDistrict);
            }
        }
        return subDistricts;
    }
}
