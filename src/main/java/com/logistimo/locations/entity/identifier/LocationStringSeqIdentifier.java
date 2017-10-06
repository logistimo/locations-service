package com.logistimo.locations.entity.identifier;

import com.logistimo.locations.entity.Identifiable;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by kumargaurav on 03/10/17.
 */
public class LocationStringSeqIdentifier implements IdentifierGenerator, Configurable {


  public static final String SEQUENCE_PREFIX = "sequence_prefix";

  private String sequencePrefix;

  // private String sequenceCallSyntax;

  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry)
      throws MappingException {

    final JdbcEnvironment jdbcEnvironment =
        serviceRegistry.getService(JdbcEnvironment.class);
    final Dialect dialect = jdbcEnvironment.getDialect();

    final ConfigurationService configurationService =
        serviceRegistry.getService(ConfigurationService.class);
    String globalEntityIdentifierPrefix =
        configurationService.getSetting("entity.identifier.prefix", String.class, "");

    sequencePrefix = ConfigurationHelper.getString(
        SEQUENCE_PREFIX,
        params,
        globalEntityIdentifierPrefix);

    sequencePrefix = "LC#" + sequencePrefix;
  }

  @Override
  public Serializable generate(SessionImplementor session, Object obj)
      throws HibernateException {

    if (obj instanceof Identifiable) {
      Identifiable identifiable = (Identifiable) obj;
      Serializable id = identifiable.getEntityName();
      if (id != null) {
        return HashGenerator.generateHash(sequencePrefix + "#" + id);
      }
    }
    return null;
  }


  private static class HashGenerator {

    static String generateHash(String str) {
      MessageDigest m = null;
      try {
        m = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
        //e.printStackTrace();
      }
      m.update(str.getBytes());
      byte[] byteData = m.digest();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < byteData.length; i++) {
        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
      }
      //return new BigInteger(1, m.digest()).toString(36);
      return sb.toString();
    }
  }
}
