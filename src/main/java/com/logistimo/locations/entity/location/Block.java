package com.logistimo.locations.entity.location;

import com.logistimo.locations.entity.AuditableEntity;
import com.logistimo.locations.entity.Identifiable;

import static com.logistimo.locations.constants.LocationConstants.HASH;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by kumargaurav on 27/02/17.
 */
@Entity
@Table(name = "BLOCK")
public class Block extends AuditableEntity implements Identifiable<String> {

  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(
      name = "assigned-sequence",
      strategy = "com.logistimo.locations.entity.identifier.LocationStringSeqIdentifier",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_prefix", value = "BL#"),
      }
  )
  @GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.SEQUENCE)
  protected String id;

  @Column(name = "BLOCKNAME")
  private String name;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name="SUBDISTID",referencedColumnName = "ID")
  private SubDistrict subdistrictId;

  @Column(name = "DISTID")
  private Long districtId;

  public Block () {}

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

  public SubDistrict getSubdistrictId() {
    return subdistrictId;
  }

  public void setSubdistrictId(SubDistrict subdistrictId) {
    this.subdistrictId = subdistrictId;
  }

  public Long getDistrictId() {
    return districtId;
  }

  public void setDistrictId(Long districtId) {
    this.districtId = districtId;
  }

  @Override
  public String getEntityName() {
    StringBuffer buf = new StringBuffer();
    buf.append(getSubdistrictId().getName()).append(HASH).append(getName());
    return buf.toString();
  }
}
