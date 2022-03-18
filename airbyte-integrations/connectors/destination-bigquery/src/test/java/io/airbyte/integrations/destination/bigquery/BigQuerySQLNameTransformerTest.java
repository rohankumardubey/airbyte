/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.bigquery;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;

class BigQuerySQLNameTransformerTest {

  private static final BigQuerySQLNameTransformer INSTANCE = new BigQuerySQLNameTransformer();
  private static final Map<String, String> RAW_TO_NORMALIZED_IDENTIFIERS = Map.of(
      "name-space", "name_space",
      "spécial_character", "special_character",
      "99namespace", "_99namespace");

  @Test
  public void testGetIdentifier() {
    assertNull(INSTANCE.getIdentifier(null));
    assertNull(INSTANCE.convertStreamName(null));
    RAW_TO_NORMALIZED_IDENTIFIERS.forEach((raw, normalized) -> {
      assertEquals(normalized, INSTANCE.getIdentifier(raw));
      assertEquals(normalized, INSTANCE.convertStreamName(raw));
    });
  }

}
