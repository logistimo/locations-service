package com.logistimo.locations.entity.location;

import com.logistimo.locations.entity.AuditableEntity;
import com.logistimo.locations.entity.Identifiable;

import static com.logistimo.locations.constants.LocationConstants.HASH;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Entity
@Table(name = "CITY",
    uniqueConstraints =
        {@UniqueConstraint(columnNames = {"COUNTRYID", "STATEID", "DISTID", "PLACENAME"})})
public class City extends AuditableEntity implements Identifiable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(
        name = "assigned-sequence",
        strategy = "com.logistimo.locations.entity.identifier.LocationStringSeqIdentifier",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "sequence_prefix", value = "CT#"),
        }
    )
    @GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.SEQUENCE)
    protected String id;

    @Column(name = "PLACENAME")
    private String name;

    @Column(name = "PLACECODE")
    private String code;

    @Column(name = "POSTALCODE")
    private String postalCode;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private PlaceType type;

    @Column(name = "COUNTRYID")
    private String countryId;

    @Column(name = "STATEID")
    private String stateId;

    @Column(name = "BLOCKID")
    private String blockId;

    @Column(name = "SUBDISTID")
    private String subdistrictId;

    @Column(name = "DISTID")
    private String districtId;

    @Column(name = "ISALIAS")
    private Boolean isAlias;

    @Column(name = "CITYKEYID")
    private String cityKeyId;

    public City () {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getSubdistrictId() {
        return subdistrictId;
    }

    public void setSubdistrictId(String subdistrictId) {
        this.subdistrictId = subdistrictId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public Boolean getAlias() {
        return isAlias;
    }

    public void setAlias(Boolean alias) {
        isAlias = alias;
    }

    public String getCityKeyId() {
        return cityKeyId;
    }

    public void setCityKeyId(String cityKeyId) {
        this.cityKeyId = cityKeyId;
    }

    @Override
    public String getEntityName() {

        StringBuffer buf = new StringBuffer();
        if (this.countryId != null) {
            buf.append(this.countryId);
            buf.append(HASH);
        }
        if (this.stateId != null) {
            buf.append(this.stateId);
            buf.append(HASH);
        }
        if (this.districtId != null) {
            buf.append(this.countryId);
            buf.append(HASH);
        }
        return buf.append(getName()).toString();
    }
}
