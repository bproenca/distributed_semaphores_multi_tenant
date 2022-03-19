package br.com.bcp;

/**
 * Only used to input some basic config data for tests.
 */
public enum Tenant {
    A ("tenant_a", "resource_lock_tenant_a", 3),
    B ("tenant_b", "resource_lock_tenant_b", 5),
    C ("tenant_c", "resource_lock_tenant_c", 8),
    D ("tenant_d", "resource_lock_tenant_d", 13);

    private final String queueName;
    private final String resourceLock;
    private final Integer numberOfMessages;

    private Tenant(String queueName, String resourceLock, Integer numberOfMessages) {
        this.queueName = queueName;
        this.resourceLock = resourceLock;
        this.numberOfMessages = numberOfMessages;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getResourceLock() {
        return resourceLock;
    }

    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }

    
}
