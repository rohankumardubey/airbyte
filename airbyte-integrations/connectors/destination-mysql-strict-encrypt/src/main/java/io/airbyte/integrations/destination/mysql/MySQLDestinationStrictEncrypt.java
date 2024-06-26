/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.mysql;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.airbyte.cdk.db.jdbc.JdbcUtils;
import io.airbyte.cdk.integrations.base.Destination;
import io.airbyte.cdk.integrations.base.IntegrationRunner;
import io.airbyte.cdk.integrations.base.spec_modification.SpecModifyingDestination;
import io.airbyte.commons.json.Jsons;
import io.airbyte.protocol.models.v0.ConnectorSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLDestinationStrictEncrypt extends SpecModifyingDestination implements Destination {

  private static final Logger LOGGER = LoggerFactory.getLogger(MySQLDestinationStrictEncrypt.class);

  public MySQLDestinationStrictEncrypt() {
    super(MySQLDestination.sshWrappedDestination());
  }

  @Override
  public ConnectorSpecification modifySpec(final ConnectorSpecification originalSpec) {
    final ConnectorSpecification spec = Jsons.clone(originalSpec);
    ((ObjectNode) spec.getConnectionSpecification().get("properties")).remove(JdbcUtils.SSL_KEY);
    return spec;
  }

  public static void main(final String[] args) throws Exception {
    final Destination destination = new MySQLDestinationStrictEncrypt();
    LOGGER.info("starting destination: {}", MySQLDestinationStrictEncrypt.class);
    try {
      new IntegrationRunner(destination).run(args);
    } catch (Exception e) {
      MySQLDestination.handleException(e);
    }
    LOGGER.info("completed destination: {}", MySQLDestinationStrictEncrypt.class);
  }

}
