

package com.logistimo.locations.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import com.logistimo.locations.entity.location.Country;
import com.logistimo.locations.entity.location.District;
import com.logistimo.locations.entity.location.State;
import com.logistimo.locations.entity.location.SubDistrict;
import com.logistimo.locations.exception.BadRequestException;
import com.logistimo.locations.repository.location.CountryRepository;
import com.logistimo.locations.repository.location.DistrictRepository;
import com.logistimo.locations.repository.location.StateRepository;
import com.logistimo.locations.repository.location.SubDistrictRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Component
public class LocationLoader {

    final static String NAME = "name";
    final static String STATES = "states";
    final static String TALUK = "taluks";
    final static String USER = "location-user";
    private final static Logger log = LoggerFactory.getLogger(LocationLoader.class);
    @Resource
    CountryRepository repository;

    @Resource
    StateRepository stRepository;

    @Resource
    DistrictRepository dstRepository;

    @Resource
    SubDistrictRepository sdstRepository;


    public void load(String json) {

        JsonObject jsonObject = null;
        JsonParser parser = new JsonParser();
        try {
            jsonObject = parser.parse(json).getAsJsonObject();
        } catch (JsonSyntaxException e) {
            log.warn("Issue with location json {} error is {}", json, e.getMessage(), e);
          throw new BadRequestException(e);
        }
        process(jsonObject);
    }

    public void load() throws IOException {
        org.springframework.core.io.Resource resource = new ClassPathResource("location.json");
        Reader fileReader = new InputStreamReader(resource.getInputStream());
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(fileReader).getAsJsonObject();
        process(jsonObject);
    }

    private void process(JsonObject json) {
        Iterator itr = json.get("data").getAsJsonObject().entrySet().iterator();
        //Country
        Country country = null;
        Country countryEntity = null;
        Set<State> states = null;
        while (itr.hasNext()) {
            states = new HashSet<>();
            String ccode = null;
            JsonObject cobject = null;
            Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) itr.next();
            ccode = entry.getKey();
            //check whether country exists or not
            countryEntity = repository.findByCode(ccode);
            country = (countryEntity != null) ? countryEntity : new Country();
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
//                    if (null != cname && ("Switzerland".equals(cname))) {
//                        continue;
//                    }
                    sobject = centry.getValue().getAsJsonObject();
                    sitr = sobject.entrySet().iterator();
                    State state = null;
                    State stateEntity = null;
                    while (sitr.hasNext()) {
                        Map.Entry<String, JsonElement> sentry = (Map.Entry<String, JsonElement>) sitr.next();
                        sname = sentry.getKey();
                        //check whether state exists or not
                        if (countryEntity != null) {
                            stateEntity = stRepository.findByName(countryEntity.getId(), sname);
                        }
                        state = (stateEntity != null) ? stateEntity : new State();
                        state.setName(sname);
                        state.setCreatedBy(USER);
                        state.setCreatedOn(new Date());
                        state.setCountry(country);
                        states.add(state);
                        dobject =
                            sentry.getValue().getAsJsonObject().entrySet().iterator().next()
                                .getValue()
                                .getAsJsonObject();
                        Iterator ditr = dobject.entrySet().iterator();
                        String dname = null;
                        District district = null;
                        District dstEntity = null;
                        Set<District> districts = new HashSet<>();
                        while (ditr.hasNext()) {
                            Map.Entry<String, JsonElement> dentry = (Map.Entry<String, JsonElement>) ditr.next();
                            dname = dentry.getKey();
                            //check whether district exists or not
                            if (stateEntity != null) {
                                dstEntity = dstRepository.findByName(stateEntity.getId(), dname);
                            }
                            district = (dstEntity != null) ? dstEntity : new District();
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
                                        dentry.getValue().getAsJsonObject().entrySet().iterator()
                                            .next().getValue()
                                            .getAsJsonArray();
                                    subDistricts =
                                        populateSubDistricts(district, dstEntity, tarray);
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

    Set<SubDistrict> populateSubDistricts(District district, District dstEntity, JsonArray jArray) {

        Set<SubDistrict> subDistricts = new HashSet<>();
        SubDistrict subDistrict = null;
        SubDistrict sdtEntity = null;
        if (jArray != null) {
            for (int i = 0; i < jArray.size(); i++) {
                //check whether subdistrict exists or not
                String name = jArray.get(i).getAsString();
                if (dstEntity != null) {
                    sdtEntity = sdstRepository.findByName(dstEntity.getId(), name);
                }
                subDistrict = (sdtEntity != null) ? sdtEntity : new SubDistrict();
                subDistrict.setName(name);
                subDistrict.setCreatedBy(USER);
                subDistrict.setCreatedOn(new Date());
                subDistrict.setDistrict(district);
                subDistricts.add(subDistrict);
            }
        }
        return subDistricts;
    }
}
