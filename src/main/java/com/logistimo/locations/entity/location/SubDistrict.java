package com.logistimo.locations.entity.location;

import com.logistimo.locations.entity.AuditableEntity;
import com.logistimo.locations.entity.Identifiable;

import static com.logistimo.locations.constants.LocationConstants.HASH;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kumargaurav on 17/01/17.
 */
@Entity
@Table(name = "SUBDISTRICT")
public class SubDistrict extends AuditableEntity implements Identifiable<String> {

  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(
      name = "assigned-sequence",
      strategy = "com.logistimo.locations.entity.identifier.LocationStringSeqIdentifier",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_prefix", value = "SDT#"),
      }
  )
  @GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.SEQUENCE)
  protected String id;

  @Column(name = "SUBDISNAME")
  private String name;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "SUBDISTID")
  private Set<Block> blocks = new HashSet<Block>();

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "DISTID", referencedColumnName = "ID")
  private District district;

  public SubDistrict() {

  }

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


  public Set<Block> getBlocks() {
    return blocks;
  }

  public void setBlocks(Set<Block> blocks) {
    this.blocks = blocks;
  }

  public District getDistrict() {
    return district;
  }

  public void setDistrict(District district) {
    this.district = district;
  }

  @Override
  public String getEntityName() {
    StringBuffer buf = new StringBuffer();
    buf.append(getDistrict().getName()).append(HASH).append(getName());
    return buf.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SubDistrict that = (SubDistrict) o;
    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
    return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}

