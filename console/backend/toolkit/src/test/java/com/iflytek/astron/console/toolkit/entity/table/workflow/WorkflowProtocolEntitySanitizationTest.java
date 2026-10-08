package com.iflytek.astron.console.toolkit.entity.table.workflow;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class WorkflowProtocolEntitySanitizationTest {

    private static final String BUSINESS_PROTOCOL =
            "{\"node\":{\"sandbox\":{\"apiKey\":\"secret\"},\"apiKey\":\"model-key\"}}";
    private static final String SYSTEM_PROTOCOL =
            "{\"node\":{\"sandbox\":{\"enabled\":true,\"uid\":\"user-1\",\"apiKey\":\"secret\"}}}";

    @Test
    void sanitizesWorkflowVersionBusinessAndSystemProtocols() {
        WorkflowVersion version = new WorkflowVersion();

        version.setData(BUSINESS_PROTOCOL);
        version.setSysData(SYSTEM_PROTOCOL);

        assertThat(version.getData())
                .contains("model-key")
                .doesNotContain("sandbox", "secret");
        assertThat(version.getSysData())
                .contains("sandbox", "enabled", "user-1")
                .doesNotContain("apiKey", "secret");
    }

    @Test
    void sanitizesTemporaryAndComparisonProtocols() {
        FlowProtocolTemp temp = new FlowProtocolTemp();
        WorkflowComparison comparison = new WorkflowComparison();

        temp.setBizProtocol(BUSINESS_PROTOCOL);
        temp.setSysProtocol(SYSTEM_PROTOCOL);
        comparison.setData(BUSINESS_PROTOCOL);

        assertThat(temp.getBizProtocol()).doesNotContain("sandbox", "secret");
        assertThat(temp.getSysProtocol())
                .contains("sandbox", "user-1")
                .doesNotContain("apiKey", "secret");
        assertThat(comparison.getData()).doesNotContain("sandbox", "secret");
    }

    @Test
    void invalidDatabaseValuesCannotBeReturnedThroughEntities() {
        String invalid = "{\"sandbox\":{\"apiKey\":\"must-not-leak\"}";
        WorkflowVersion version = new WorkflowVersion();
        FlowProtocolTemp temp = new FlowProtocolTemp();
        WorkflowComparison comparison = new WorkflowComparison();

        version.setData(invalid);
        version.setSysData(invalid);
        temp.setBizProtocol(invalid);
        temp.setSysProtocol(invalid);
        comparison.setData(invalid);

        assertThat(version.getData()).isNull();
        assertThat(version.getSysData()).isNull();
        assertThat(temp.getBizProtocol()).isNull();
        assertThat(temp.getSysProtocol()).isNull();
        assertThat(comparison.getData()).isNull();
    }
}
